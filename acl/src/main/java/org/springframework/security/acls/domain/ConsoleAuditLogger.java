package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.AuditableAccessControlEntry;
import org.springframework.util.Assert;

/**
 * A basic implementation of {@link AuditLogger}.
 *
 * @author Ben Alex
 */
public class ConsoleAuditLogger implements AuditLogger {

    @Override
    public void logIfNeeded(boolean granted, AccessControlEntry ace) {
        Assert.notNull(ace, "AccessControlEntry required");

        if (ace instanceof AuditableAccessControlEntry) {
            AuditableAccessControlEntry auditableAce = (AuditableAccessControlEntry) ace;
            if (granted && auditableAce.isAuditSuccess()) {
                System.out.println("GRANTED due to ACE: " + ace);
            }
            else if (!granted && auditableAce.isAuditFailure()) {
                System.out.println("DENIED due to ACE: " + ace);
            }
        }
    }

}
