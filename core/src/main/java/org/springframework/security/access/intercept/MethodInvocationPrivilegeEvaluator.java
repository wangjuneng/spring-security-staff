package org.springframework.security.access.intercept;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

public class MethodInvocationPrivilegeEvaluator implements InitializingBean {

    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory.getLog(MethodInvocationPrivilegeEvaluator.class);

    // ~ Instance fields
    // ================================================================================================

    private AbstractSecurityInterceptor securityInterceptor;

    // ~ Methods
    // ========================================================================================================

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(securityInterceptor, "SecurityInterceptor required");
    }

    public boolean isAllowed(MethodInvocation mi, Authentication authentication) {
        Assert.notNull(mi, "MethodInvocation required");
        Assert.notNull(mi.getMethod(), "MethodInvocation must provide a non-null getMethod()");

        Collection<ConfigAttribute> attrs = securityInterceptor.obtainSecurityMetadataSource().getAttributes(mi);

        if (attrs == null) {
            if (securityInterceptor.isRejectPublicInvocations()) {
                return false;
            }

            return true;
        }

        if (authentication == null || authentication.getAuthorities().isEmpty()) {
            return false;
        }

        try {
            securityInterceptor.getAccessDecisionManager().decide(authentication, mi, attrs);
        }
        catch (AccessDeniedException unauthorized) {
            if (logger.isDebugEnabled()) {
                logger.debug(mi.toString() + " denied for " + authentication.toString(), unauthorized);
            }

            return false;
        }

        return true;
    }

    public void setSecurityInterceptor(AbstractSecurityInterceptor securityInterceptor) {
        Assert.notNull(securityInterceptor, "AbstractSecurityInterceptor cannot be null");
        Assert.isTrue(MethodInvocation.class.equals(securityInterceptor.getSecureObjectClass()),
            "AbstractSecurityInterceptor does not support MethodInvocations");
        Assert.notNull(securityInterceptor.getAccessDecisionManager(),
            "AbstractSecurityInterceptor must provide a non-null AccessDecisionManager");
        this.securityInterceptor = securityInterceptor;
    }
}
