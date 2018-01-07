package org.springframework.security.access.method;

import java.lang.reflect.Method;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityMetadataSource;

public interface MethodSecurityMetadataSource extends SecurityMetadataSource {
    public Collection<ConfigAttribute> getAttributes(Method method, Class<?> targetClass);
}
