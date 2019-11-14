package com.sberbank.crm.loginservice.dao;

import com.sberbank.crm.loginservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    User findByActivationCode(String code);
}
