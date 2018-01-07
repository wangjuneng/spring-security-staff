package org.springframework.security.core.context;

import org.springframework.util.Assert;

/**
 * A <code>ThreadLocal</code>-based implementation of
 * {@link SecurityContextHolderStrategy}.
 *
 * @author Ben Alex
 *
 * @see java.lang.ThreadLocal
 * @see org.springframework.security.core.context.web.SecurityContextPersistenceFilter
 */
public final class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<SecurityContext>();
    
    @Override
    public void clearContext() {
        contextHolder.remove();
    }

    @Override
    public SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();
        if(ctx == null){
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }

    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
