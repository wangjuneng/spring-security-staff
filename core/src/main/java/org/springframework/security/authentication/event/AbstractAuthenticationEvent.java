package org.springframework.security.authentication.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.Authentication;

public abstract class AbstractAuthenticationEvent extends ApplicationEvent {
    // ~ Constructors
    // ===================================================================================================

    public AbstractAuthenticationEvent(Authentication authentication) {
        super(authentication);
    }

    // ~ Methods
    // ========================================================================================================

    /**
     * Getters for the <code>Authentication</code> request that caused the event. Also available from
     * <code>super.getSource()</code>.
     *
     * @return the authentication request
     */
    public Authentication getAuthentication() {
        return (Authentication) super.getSource();
    }
}
