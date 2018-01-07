package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationCredentialsNotFoundException extends AuthenticationException {

    /**
     * Constructs an <code>AuthenticationCredentialsNotFoundException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public AuthenticationCredentialsNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>AuthenticationCredentialsNotFoundException</code> with the specified message and root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public AuthenticationCredentialsNotFoundException(String msg, Throwable t) {
        super(msg, t);
    }
}
