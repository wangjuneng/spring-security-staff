package org.springframework.security.core;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;

public class SpringSecurityMessageSource extends ResourceBundleMessageSource {

    public SpringSecurityMessageSource() {
        setBasename("org.springframework.security.messages");
    }

    // ~ Methods
    // ========================================================================================================

    public static MessageSourceAccessor getAccessor() {
        return new MessageSourceAccessor(new SpringSecurityMessageSource());
    }
}
