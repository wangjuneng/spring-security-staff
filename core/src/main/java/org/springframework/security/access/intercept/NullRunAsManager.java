package org.springframework.security.access.intercept;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

public final class NullRunAsManager implements RunAsManager {
    public Authentication buildRunAs(Authentication authentication, Object object, Collection<ConfigAttribute> config) {
        return null;
    }

    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }
}
