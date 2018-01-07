package org.springframework.security.access;

import java.util.Collection;

import org.springframework.aop.framework.AopInfrastructureBean;
import org.springframework.security.core.Authentication;

public interface PermissionCacheOptimizer extends AopInfrastructureBean {
    /**
     * Optimises the permission cache for anticipated operation on the supplied collection of objects. Usually this will
     * entail batch loading of permissions for the objects in the collection.
     *
     * @param a the user for whom permissions should be obtained.
     * @param objects the (non-null) collection of domain objects for which permissions should be retrieved.
     */
    void cachePermissionsFor(Authentication a, Collection<?> objects);

}
