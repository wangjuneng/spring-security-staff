package org.springframework.security.access.method;

import java.util.Collection;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.security.access.ConfigAttribute;

/**
 * Abstract implementation of <tt>MethodSecurityMetadataSource</tt> which resolves the secured object type to a
 * MethodInvocation.
 *
 * @author Ben Alex
 * @author Luke Taylor
 */
public abstract class AbstractMethodSecurityMetadataSource implements MethodSecurityMetadataSource {

    protected final Log
    logger = LogFactory.getLog(getClass());

    public final Collection<ConfigAttribute> getAttributes(Object object) {
        if (object instanceof MethodInvocation) {
            MethodInvocation mi = (MethodInvocation) object;
            Object target = mi.getThis();

            Class<?> targetClass = null;
            if (target != null) {
                targetClass = target instanceof Class<?> ? (Class<?>) target : AopProxyUtils
                    .ultimateTargetClass(target);
            }

            Collection<ConfigAttribute> attrs = getAttributes(mi.getMethod(), targetClass);

            if (attrs != null && !attrs.isEmpty()) {
                return attrs;
            }
            if (target != null && !(target instanceof Class<?>)) {
                attrs = getAttributes(mi.getMethod(), target.getClass());
            }
            return attrs;
        }

        throw new IllegalArgumentException("Object must be a non-null MethodInvocation");
    }

    public final boolean supports(Class<?> clazz) {
        return (MethodInvocation.class.isAssignableFrom(clazz));
    }
}
