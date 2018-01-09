package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/**
 * Represents an <code>Authentication.getPrincipal()</code> as a <code>Sid</code>.
 * <p>
 * This is a basic implementation that simply uses the <code>String</code>-based principal for <code>Sid</code>
 * comparison. More complex principal objects may wish to provide an alternative <code>Sid</code> implementation that
 * uses some other identifier.
 * </p>
 *
 * @author Ben Alex
 */
public class PrincipalSid implements Sid {
    // ~ Instance fields
    // ================================================================================================

    private final String principal;

    // ~ Constructors
    // ===================================================================================================

    public PrincipalSid(String principal) {
        Assert.hasText(principal, "Principal required");
        this.principal = principal;
    }

    public PrincipalSid(Authentication authentication) {
        Assert.notNull(authentication, "Authentication required");
        Assert.notNull(authentication.getPrincipal(), "Principal required");

        if (authentication.getPrincipal() instanceof UserDetails) {
            this.principal = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        else {
            this.principal = authentication.getPrincipal().toString();
        }
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public boolean equals(Object object) {
        if ((object == null) || !(object instanceof PrincipalSid)) {
            return false;
        }

        // Delegate to getPrincipal() to perform actual comparison (both should be
        // identical)
        return ((PrincipalSid) object).getPrincipal().equals(this.getPrincipal());
    }

    @Override
    public int hashCode() {
        return this.getPrincipal().hashCode();
    }

    public String getPrincipal() {
        return principal;
    }

    @Override
    public String toString() {
        return "PrincipalSid[" + this.principal + "]";
    }
}
