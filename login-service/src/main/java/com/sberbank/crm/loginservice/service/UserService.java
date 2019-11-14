package com.sberbank.crm.loginservice.service;

import com.sberbank.crm.loginservice.dao.RoleRepository;
import com.sberbank.crm.loginservice.dao.UserRepository;
import com.sberbank.crm.loginservice.entities.Role;
import com.sberbank.crm.loginservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void addUser(User user, String description) {
        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByDescription(description);
        newUser.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        newUser.setActivationCode(UUID.randomUUID().toString());
        newUser.setActive(false);
        userRepository.save(newUser);
        mailSenderService.sendConfirmMsg(newUser.getEmail(), newUser.getName(), newUser.getActivationCode());
    }

    public boolean isUserExist(User user) {
        return userRepository.findUserByNameOrEmail(user.getName(), user.getEmail()) != null;
    }

    public boolean activeUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        } else {
            user.setActivationCode(null);
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
    }
}
