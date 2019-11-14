package com.sberbank.crm.loginservice.dao;

import com.sberbank.crm.loginservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByDescription(String description);
}

