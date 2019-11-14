package com.sberbank.crm.loginservice.service;

import com.sberbank.crm.loginservice.dao.RoleRepository;
import com.sberbank.crm.loginservice.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<String> getAllRolesDescription() {
        List<Role> roles = roleRepository.findAll();
        List<String> rolesDescription = new ArrayList<>();

        for (Role role : roles) {
            rolesDescription.add(role.getDescription());
        }

        return rolesDescription;
    }
}
