package org.springframework.security.acls.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.hierarchicalroles.NullRoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

/**
 * Basic implementation of {@link SidRetrievalStrategy} that creates a {@link Sid} for the principal, as well as every
 * granted authority the principal holds. Can optionally have a <tt>RoleHierarchy</tt> injected in order to determine
 * the extended list of authorities that the principal is assigned.
 * <p>
 * The returned array will always contain the {@link PrincipalSid} before any {@link GrantedAuthoritySid} elements.
 *
 * @author Ben Alex
 */
public class SidRetrievalStrategyImpl implements SidRetrievalStrategy {

    private RoleHierarchy roleHierarchy = new NullRoleHierarchy();

    public SidRetrievalStrategyImpl() {
    }

    public SidRetrievalStrategyImpl(RoleHierarchy roleHierarchy) {
        Assert.notNull(roleHierarchy, "RoleHierarchy must not be null");
        this.roleHierarchy = roleHierarchy;
    }

    @Override
    public List<Sid> getSids(Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = roleHierarchy
            .getReachableGrantedAuthorities(authentication.getAuthorities());
        List<Sid> sids = new ArrayList<Sid>(authorities.size() + 1);

        sids.add(new PrincipalSid(authentication));

        for (GrantedAuthority authority : authorities) {
            sids.add(new GrantedAuthoritySid(authority));
        }

        return sids;
    }

}
