package org.springframework.security.core.context;

import org.springframework.util.Assert;

public class InheritableThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    private static final ThreadLocal<SecurityContext> contextHolder = new InheritableThreadLocal<SecurityContext>();

    @Override
    public void clearContext() {
        contextHolder.remove();

    }

    @Override
    public SecurityContext getContext() {
        SecurityContext ctx = contextHolder.get();

        if (ctx == null) {
            ctx = createEmptyContext();
            contextHolder.set(ctx);
        }

        return ctx;
    }

    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder.set(context);
    }

    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
