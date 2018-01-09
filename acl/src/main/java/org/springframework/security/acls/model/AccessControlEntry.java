package org.springframework.security.acls.model;

import java.io.Serializable;

/**
 * Represents an individual permission assignment within an {@link Acl}.
 * <p>
 * Instances MUST be immutable, as they are returned by <code>Acl</code> and should not allow client modification.
 * </p>
 *
 * @author Ben Alex
 */
public interface AccessControlEntry extends Serializable {
    // ~ Methods
    // ========================================================================================================

    Acl getAcl();

    /**
     * Obtains an identifier that represents this ACE.
     *
     * @return the identifier, or <code>null</code> if unsaved
     */
    Serializable getId();

    Permission getPermission();

    Sid getSid();

    /**
     * Indicates the permission is being granted to the relevant Sid. If false, indicates the permission is being
     * revoked/blocked.
     *
     * @return true if being granted, false otherwise
     */
    boolean isGranting();
}
