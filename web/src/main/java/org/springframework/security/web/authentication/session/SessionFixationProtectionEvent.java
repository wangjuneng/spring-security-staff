package org.springframework.security.web.authentication.session;

import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class SessionFixationProtectionEvent extends AbstractAuthenticationEvent {
    private final String oldSessionId;

    private final String newSessionId;

    /**
     * Constructs a new session fixation protection event.
     *
     * @param authentication The authentication object
     * @param oldSessionId The old session ID before it was changed
     * @param newSessionId The new session ID after it was changed
     */
    public SessionFixationProtectionEvent(Authentication authentication, String oldSessionId, String newSessionId) {
        super(authentication);
        Assert.hasLength(oldSessionId, "oldSessionId must have length");
        Assert.hasLength(newSessionId, "newSessionId must have length");
        this.oldSessionId = oldSessionId;
        this.newSessionId = newSessionId;
    }
    
    // ~ Methods
    // ========================================================================================================

    /**
     * Getter for the session ID before it was changed.
     *
     * @return the old session ID.
     */
    public String getOldSessionId() {
        return this.oldSessionId;
    }

    /**
     * Getter for the session ID after it was changed.
     *
     * @return the new session ID.
     */
    public String getNewSessionId() {
        return this.newSessionId;
    }
}
