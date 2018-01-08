package org.springframework.security.concurrent;

import java.util.concurrent.Executor;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.util.Assert;

/**
 * An {@link Executor} which wraps each {@link Runnable} in a {@link DelegatingSecurityContextRunnable}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingSecurityContextExecutor extends AbstractDelegatingSecurityContextSupport implements Executor {

    private final Executor delegate;

    /**
     * Creates a new {@link DelegatingSecurityContextExecutor} that uses the specified {@link SecurityContext}.
     *
     * @param delegateExecutor the {@link Executor} to delegate to. Cannot be null.
     * @param securityContext the {@link SecurityContext} to use for each {@link DelegatingSecurityContextRunnable} or
     *            null to default to the current {@link SecurityContext}
     */
    public DelegatingSecurityContextExecutor(Executor delegateExecutor, SecurityContext securityContext) {
        super(securityContext);
        Assert.notNull(delegateExecutor, "delegateExecutor cannot be null");
        this.delegate = delegateExecutor;
    }

    /**
     * Creates a new {@link DelegatingSecurityContextExecutor} that uses the current {@link SecurityContext} from the
     * {@link SecurityContextHolder} at the time the task is submitted.
     *
     * @param delegate the {@link Executor} to delegate to. Cannot be null.
     */
    public DelegatingSecurityContextExecutor(Executor delegate) {
        this(delegate, null);
    }

    @Override
    public void execute(Runnable task) {
        task = wrap(task);

        delegate.execute(task);
    }

    protected final Executor getDelegateExecutor() {
        return delegate;
    }

}
