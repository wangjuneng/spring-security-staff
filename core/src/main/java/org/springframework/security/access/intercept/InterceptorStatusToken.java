package org.springframework.security.access.intercept;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.context.SecurityContext;

/**
 * A return object received by {@link AbstractSecurityInterceptor} subclasses.
 * <p>
 * This class reflects the status of the security interception, so that the final call to
 * {@link org.springframework.security.access.intercept.AbstractSecurityInterceptor#afterInvocation(InterceptorStatusToken, Object)}
 * can tidy up correctly.
 *
 * @author Ben Alex
 */
public class InterceptorStatusToken {

    private SecurityContext securityContext;

    private Collection<ConfigAttribute> attr;

    private Object secureObject;

    private boolean contextHolderRefreshRequired;

    public InterceptorStatusToken(SecurityContext securityContext, boolean contextHolderRefreshRequired,
        Collection<ConfigAttribute> attributes, Object secureObject) {
        this.securityContext = securityContext;
        this.contextHolderRefreshRequired = contextHolderRefreshRequired;
        this.attr = attributes;
        this.secureObject = secureObject;
    }

    // ~ Methods
    // ========================================================================================================

    public Collection<ConfigAttribute> getAttributes() {
        return attr;
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public Object getSecureObject() {
        return secureObject;
    }

    public boolean isContextHolderRefreshRequired() {
        return contextHolderRefreshRequired;
    }

}
