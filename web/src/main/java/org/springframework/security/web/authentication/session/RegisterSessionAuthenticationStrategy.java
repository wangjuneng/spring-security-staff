package org.springframework.security.web.authentication.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

public class RegisterSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final SessionRegistry sessionRegistry;

    /**
     * @param sessionRegistry the session registry which should be updated when the authenticated session is changed.
     */
    public RegisterSessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
        Assert.notNull(sessionRegistry, "The sessionRegistry cannot be null");
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
        throws SessionAuthenticationException {
        sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());

    }

}
