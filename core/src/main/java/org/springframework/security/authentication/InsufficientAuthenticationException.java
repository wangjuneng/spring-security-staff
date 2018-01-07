package org.springframework.security.authentication;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown if an authentication request is rejected because the credentials are not sufficiently trusted.
 * <p>
 * {@link org.springframework.security.access.AccessDecisionVoter}s will typically throw this exception if they are
 * dissatisfied with the level of the authentication, such as if performed using a remember-me mechanism or anonymously.
 * The {@code ExceptionTranslationFilter} will then typically cause the {@code AuthenticationEntryPoint} to be called,
 * allowing the principal to authenticate with a stronger level of authentication.
 *
 * @author Ben Alex
 */
public class InsufficientAuthenticationException extends AuthenticationException {
    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructs an <code>InsufficientAuthenticationException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public InsufficientAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>InsufficientAuthenticationException</code> with the specified message and root cause.
     *
     * @param msg the detail message
     * @param t root cause
     */
    public InsufficientAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }
}
