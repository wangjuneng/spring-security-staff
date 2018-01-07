package org.springframework.security.access.intercept.aspectj;

import org.aspectj.lang.JoinPoint;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;

public abstract class AspectJMethodSecurityInterceptor extends MethodSecurityInterceptor {
    /**
     * Method that is suitable for user with @Aspect notation.
     *
     * @param jp The AspectJ joint point being invoked which requires a security decision
     * @return The returned value from the method invocation
     * @throws Throwable if the invocation throws one
     */
    public Object invoke(JoinPoint jp) throws Throwable {
        return super.invoke(new MethodInvocationAdapter(jp));
    }

    /**
     * Method that is suitable for user with traditional AspectJ-code aspects.
     *
     * @param jp The AspectJ joint point being invoked which requires a security decision
     * @param advisorProceed the advice-defined anonymous class that implements {@code AspectJCallback} containing a
     *            simple {@code return proceed();} statement
     * @return The returned value from the method invocation
     */
    public Object invoke(JoinPoint jp, AspectJCallback advisorProceed) {
        InterceptorStatusToken token = super.beforeInvocation(new MethodInvocationAdapter(jp));

        Object result;
        try {
            result = advisorProceed.proceedWithObject();
        }
        finally {
            super.finallyInvocation(token);
        }

        return super.afterInvocation(token, result);
    }
}
