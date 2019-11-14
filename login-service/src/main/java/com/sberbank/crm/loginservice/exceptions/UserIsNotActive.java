package com.sberbank.crm.loginservice.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserIsNotActive extends AuthenticationException {
    public UserIsNotActive(String msg) {
        super(msg);
    }

    public UserIsNotActive(String msg, Throwable t) {
        super(msg, t);
    }
}
