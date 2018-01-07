package org.springframework.security.access.hierarchicalroles;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * The simple interface of a role hierarchy.
 *
 * @author Michael Mayr
 */
public interface RoleHierarchy {
    /**
     * Returns an array of all reachable authorities.
     * <p>
     * Reachable authorities are the directly assigned authorities plus all authorities that are (transitively)
     * reachable from them in the role hierarchy.
     * <p>
     * Example:<br>
     * Role hierarchy: ROLE_A &gt; ROLE_B and ROLE_B &gt; ROLE_C.<br>
     * Directly assigned authority: ROLE_A.<br>
     * Reachable authorities: ROLE_A, ROLE_B, ROLE_C.
     *
     * @param authorities - List of the directly assigned authorities.
     * @return List of all reachable authorities given the assigned authorities.
     */
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(
        Collection<? extends GrantedAuthority> authorities);
}
