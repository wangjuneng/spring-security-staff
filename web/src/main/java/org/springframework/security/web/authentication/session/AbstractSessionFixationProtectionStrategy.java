package org.springframework.security.web.authentication.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.util.WebUtils;

public abstract class AbstractSessionFixationProtectionStrategy implements SessionAuthenticationStrategy,
    ApplicationEventPublisherAware {

    protected final Log logger = LogFactory.getLog(this.getClass());

    private ApplicationEventPublisher applicationEventPublisher;

    private boolean alwaysCreateSession;

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response)
        throws SessionAuthenticationException {
        boolean hadSessionAlready = request.getSession(false) != null;

        if (!hadSessionAlready && !alwaysCreateSession) {
            // Session fixation isn't a problem if there's no session
            return;
        }

        // Create new session if necessary
        HttpSession session = request.getSession();

        if (hadSessionAlready && request.isRequestedSessionIdValid()) {
            String originalSessionId;
            String newSessionId;
            Object mutex = WebUtils.getSessionMutex(session);

            synchronized (mutex) {
                // We need to migrate to a new session
                originalSessionId = session.getId();
                session = applySessionFixation(request);
                newSessionId = session.getId();
            }

            if (originalSessionId.equals(newSessionId)) {
                logger
                    .warn("Your servlet container did not change the session ID when a new session was created. You will"
                        + " not be adequately protected against session-fixation attacks");
            }

            onSessionChange(originalSessionId, session, authentication);
        }

    }

    private void onSessionChange(String oldSessionId, HttpSession session, Authentication authentication) {
        applicationEventPublisher.publishEvent(new SessionFixationProtectionEvent(authentication, oldSessionId, session
            .getId()));

    }

    /**
     * Applies session fixation
     *
     * @param request the {@link HttpServletRequest} to apply session fixation protection for
     * @return the new {@link HttpSession} to use. Cannot be null.
     */
    abstract HttpSession applySessionFixation(HttpServletRequest request);

    /**
     * Sets the {@link ApplicationEventPublisher} to use for submitting {@link SessionFixationProtectionEvent}. The
     * default is to not submit the {@link SessionFixationProtectionEvent}.
     *
     * @param applicationEventPublisher the {@link ApplicationEventPublisher}. Cannot be null.
     */
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        Assert.notNull(applicationEventPublisher, "applicationEventPublisher cannot be null");
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void setAlwaysCreateSession(boolean alwaysCreateSession) {
        this.alwaysCreateSession = alwaysCreateSession;
    }

    protected static final class NullEventPublisher implements ApplicationEventPublisher {
        public void publishEvent(ApplicationEvent event) {
        }

        public void publishEvent(Object event) {
        }
    }
}
