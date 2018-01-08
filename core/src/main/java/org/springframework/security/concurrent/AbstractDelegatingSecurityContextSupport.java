package org.springframework.security.concurrent;

import java.util.concurrent.Callable;

import org.springframework.security.core.context.SecurityContext;

/**
 * An internal support class that wraps {@link Callable} with {@link DelegatingSecurityContextCallable} and
 * {@link Runnable} with {@link DelegatingSecurityContextRunnable}
 *
 * @author Rob Winch
 * @since 3.2
 */
public abstract class AbstractDelegatingSecurityContextSupport {
    private final SecurityContext securityContext;

    /**
     * Creates a new {@link AbstractDelegatingSecurityContextSupport} that uses the specified {@link SecurityContext}.
     *
     * @param securityContext the {@link SecurityContext} to use for each {@link DelegatingSecurityContextRunnable} and
     *            each {@link DelegatingSecurityContextCallable} or null to default to the current
     *            {@link SecurityContext}.
     */
    AbstractDelegatingSecurityContextSupport(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    protected final Runnable wrap(Runnable delegate) {
        return DelegatingSecurityContextRunnable.create(delegate, securityContext);
    }

    protected final <T> Callable<T> wrap(Callable<T> delegate) {
        return DelegatingSecurityContextCallable.create(delegate, securityContext);
    }
}
