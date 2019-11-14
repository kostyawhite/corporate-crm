package com.sberbank.crm.loginservice.service;

import com.sberbank.crm.loginservice.dao.RoleRepository;
import com.sberbank.crm.loginservice.dao.UserRepository;
import com.sberbank.crm.loginservice.entities.Role;
import com.sberbank.crm.loginservice.entities.User;
import com.sberbank.crm.loginservice.exceptions.UserIsNotActive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, UserIsNotActive {
        User user = userRepository.findUserByNameOrEmail(userName, userName);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("Пользователь %s не найден!", userName));
        }

        if (!user.isActive()) {
            throw new UserIsNotActive(String.format("Пользователь %s не активен!", userName));
        }

        Set<Role> roles = user.getRoles();

        List<GrantedAuthority> grantList = new ArrayList<>();
        if (roles != null) {
            for (Role role : roles) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getRole());
                grantList.add(authority);
            }
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(), grantList
        );
    }
}
