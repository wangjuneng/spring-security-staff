package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.AccessControlEntry;

public interface AuditLogger {
    // ~ Methods
    // ========================================================================================================

    void logIfNeeded(boolean granted, AccessControlEntry ace);

}
