package org.springframework.security.acls.domain;

import java.util.List;

/**
 * Provides a simple mechanism to retrieve {@link Permission} instances from integer
 * masks.
 *
 * @author Ben Alex
 * @since 2.0.3
 *
 */
import org.springframework.security.acls.model.Permission;

public interface PermissionFactory {
    /**
     * Dynamically creates a <code>CumulativePermission</code> or <code>BasePermission</code> representing the active
     * bits in the passed mask.
     *
     * @param mask to build
     * @return a Permission representing the requested object
     */
    Permission buildFromMask(int mask);

    Permission buildFromName(String name);

    List<Permission> buildFromNames(List<String> names);
}
