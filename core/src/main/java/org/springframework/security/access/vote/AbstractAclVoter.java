package org.springframework.security.access.vote;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;

/**
 * Provides helper methods for writing domain object ACL voters. Not bound to any particular ACL system.
 *
 * @author Ben Alex
 */
public abstract class AbstractAclVoter implements AccessDecisionVoter<MethodInvocation> {
    // ~ Instance fields
    // ================================================================================================

    private Class<?> processDomainObjectClass;

    protected Object getDomainObjectInstance(MethodInvocation invocation) {
        Object[] args;
        Class<?>[] params;

        params = invocation.getMethod().getParameterTypes();
        args = invocation.getArguments();

        for (int i = 0; i < params.length; i++) {
            if (processDomainObjectClass.isAssignableFrom(Param.class)) {
                return args[i];
            }
        }

        throw new AuthorizationServiceException("MethodInvocation: " + invocation
            + " did not provide any argument of type: " + processDomainObjectClass);

    }

    public Class<?> getProcessDomainObjectClass() {
        return processDomainObjectClass;
    }

    public void setProcessDomainObjectClass(Class<?> processDomainObjectClass) {
        Assert.notNull(processDomainObjectClass, "processDomainObjectClass cannot be set to null");
        this.processDomainObjectClass = processDomainObjectClass;
    }

    /**
     * This implementation supports only <code>MethodSecurityInterceptor</code>, because it queries the presented
     * <code>MethodInvocation</code>.
     *
     * @param clazz the secure object
     * @return <code>true</code> if the secure object is <code>MethodInvocation</code>, <code>false</code> otherwise
     */
    public boolean supports(Class<?> clazz) {
        return (MethodInvocation.class.isAssignableFrom(clazz));
    }
}
