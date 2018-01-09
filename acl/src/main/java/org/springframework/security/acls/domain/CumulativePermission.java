package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Permission;

/**
 * Represents a <code>Permission</code> that is constructed at runtime from other permissions.
 * <p>
 * Methods return <code>this</code>, in order to facilitate method chaining.
 * </p>
 *
 * @author Ben Alex
 */
public class CumulativePermission extends AbstractPermission {

    private String pattern = THIRTY_TWO_RESERVED_OFF;

    public CumulativePermission() {
        super(0, '.');
    }

    public CumulativePermission clear(Permission permission) {
        this.mask &= ~permission.getMask();
        this.pattern = AclFormattingUtils.demergePatterns(this.pattern, permission.getPattern());

        return this;
    }

    public CumulativePermission clear() {
        this.mask = 0;
        this.pattern = THIRTY_TWO_RESERVED_OFF;

        return this;
    }

    public CumulativePermission set(Permission permission) {
        this.mask |= permission.getMask();
        this.pattern = AclFormattingUtils.mergePatterns(this.pattern, permission.getPattern());

        return this;
    }

    public String getPattern() {
        return this.pattern;
    }
}
