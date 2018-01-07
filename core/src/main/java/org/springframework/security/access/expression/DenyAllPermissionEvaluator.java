package org.springframework.security.access.expression;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public class DenyAllPermissionEvaluator implements PermissionEvaluator {

    private final Log logger = LogFactory.getLog(getClass());

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        logger
            .warn("Denying user " + authentication.getName() + " permission '" + permission + "' on object " + targetDomainObject);
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
        Object permission) {
        logger.warn("Denying user " + authentication.getName() + " permission '"
            + permission + "' on object with Id '" + targetId);
    return false;
    }

}
