package org.springframework.security.web.access;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.util.Assert;

/**
 * Allows users to determine whether they have privileges for a given web URI.
 *
 * @author Ben Alex
 * @author Luke Taylor
 * @since 3.0
 */
public class DefaultWebInvocationPrivilegeEvaluator implements WebInvocationPrivilegeEvaluator {

    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory.getLog(DefaultWebInvocationPrivilegeEvaluator.class);
    
    
    private final AbstractSecurityInterceptor securityInterceptor;
    
    public DefaultWebInvocationPrivilegeEvaluator(AbstractSecurityInterceptor securityInterceptor){
        Assert.notNull(securityInterceptor, "SecurityInterceptor cannot be null");
        Assert.isTrue(
                FilterInvocation.class.equals(securityInterceptor.getSecureObjectClass()),
                "AbstractSecurityInterceptor does not support FilterInvocations");
        
        this.securityInterceptor = securityInterceptor;
    }

    @Override
    public boolean isAllowed(String url, Authentication authentication) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication) {
        Assert.notNull(uri,"url parameter is required");
        
        FilterInvocation fi = new FilterInvocation(contextPath, uri,method);
        
        Collection<ConfigAttribute> attrs = securityInterceptor.obtainSecurityMetadataSource().getAttributes(fi);
        
        if(attrs == null){
            if (securityInterceptor.isRejectPublicInvocations()) {
                return false;
            }

            return true;
        }
        
        
        if(authentication == null){
            return false;
        }
        
        try {
            securityInterceptor.getAccessDecisionManager().decide(authentication, fi,
                    attrs);
        }
        catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(fi.toString() + " denied for " + authentication.toString(),
                        unauthorized);
            }

            return false;
        }
        
        return true;
    }

}
