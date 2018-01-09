package org.springframework.security.acls.domain;

import org.springframework.security.acls.model.Permission;

/**
 * A set of standard permissions.
 *
 * <p>
 * You may subclass this class to add additional permissions, or use this class as a guide
 * for creating your own permission classes.
 * </p>
 *
 * @author Ben Alex
 */
public class BasePermission extends AbstractPermission {
    public static final Permission READ = new BasePermission(1 << 0, 'R'); // 1
    public static final Permission WRITE = new BasePermission(1 << 1, 'W'); // 2
    public static final Permission CREATE = new BasePermission(1 << 2, 'C'); // 4
    public static final Permission DELETE = new BasePermission(1 << 3, 'D'); // 8
    public static final Permission ADMINISTRATION = new BasePermission(1 << 4, 'A'); // 16

    protected BasePermission(int mask) {
        super(mask);
    }

    protected BasePermission(int mask, char code) {
        super(mask, code);
    }
    
    public static void main(String[] args){
        System.out.println(ADMINISTRATION.getPattern());
    }
}
