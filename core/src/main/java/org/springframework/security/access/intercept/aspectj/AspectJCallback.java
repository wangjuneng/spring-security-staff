package org.springframework.security.access.intercept.aspectj;

/**
 * Called by the {@link AspectJMethodSecurityInterceptor} when it wishes for the AspectJ processing to continue.
 * Typically implemented in the <code>around()</code> advice as a simple <code>return proceed();</code> statement.
 *
 * @author Ben Alex
 */
public interface AspectJCallback {
    // ~ Methods
    // ========================================================================================================

    Object proceedWithObject();
}
