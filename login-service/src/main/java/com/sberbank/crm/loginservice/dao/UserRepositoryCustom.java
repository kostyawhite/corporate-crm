package com.sberbank.crm.loginservice.dao;

import com.sberbank.crm.loginservice.entities.User;

public interface UserRepositoryCustom {
    User findUserByNameOrEmail(String username, String email);
}
