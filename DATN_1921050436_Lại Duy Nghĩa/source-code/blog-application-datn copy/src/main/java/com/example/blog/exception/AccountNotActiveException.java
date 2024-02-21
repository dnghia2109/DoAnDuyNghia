package com.example.blog.exception;

import org.springframework.security.core.AuthenticationException;


public class AccountNotActiveException extends AuthenticationException {
    public AccountNotActiveException(String message) {
        super(message);
    }

    public AccountNotActiveException(String message, Throwable t) {
        super(message, t);
    }
}
