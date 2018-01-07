package org.springframework.security.core.context;

import org.springframework.util.Assert;

public class GlobalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    // ~ Static fields/initializers
    // =====================================================================================

    private static SecurityContext contextHolder;

    @Override
    public void clearContext() {
        contextHolder = null;

    }

    @Override
    public SecurityContext getContext() {
        if (contextHolder == null) {
            contextHolder = new SecurityContextImpl();
        }

        return contextHolder;
    }

    @Override
    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        contextHolder = context;
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
