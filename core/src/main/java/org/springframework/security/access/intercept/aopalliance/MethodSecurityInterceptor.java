package org.springframework.security.access.intercept.aopalliance;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.method.MethodSecurityMetadataSource;

/**
 * Provides security interception of AOP Alliance based method invocations.
 * <p>
 * The <code>SecurityMetadataSource</code> required by this security interceptor is of type
 * {@link MethodSecurityMetadataSource}. This is shared with the AspectJ based security interceptor (
 * <code>AspectJSecurityInterceptor</code>), since both work with Java <code>Method</code>s.
 * <p>
 * Refer to {@link AbstractSecurityInterceptor} for details on the workflow.
 *
 * @author Ben Alex
 * @author Rob Winch
 */
public class MethodSecurityInterceptor extends AbstractSecurityInterceptor implements MethodInterceptor {

    private MethodSecurityMetadataSource securityMetadataSource;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        InterceptorStatusToken token = super.beforeInvocation(invocation);

        Object result;
        try {
            result = invocation.proceed();
        }
        finally {
            super.finallyInvocation(token);
        }
        return super.afterInvocation(token, result);
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return MethodInvocation.class;
    }

    public MethodSecurityMetadataSource getSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public void setSecurityMetadataSource(MethodSecurityMetadataSource newSource) {
        this.securityMetadataSource = newSource;
    }

}
