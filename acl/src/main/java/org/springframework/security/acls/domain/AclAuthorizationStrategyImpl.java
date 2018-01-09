package org.springframework.security.acls.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.SidRetrievalStrategy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

/**
 * Default implementation of {@link AclAuthorizationStrategy}.
 * <p>
 * Permission will be granted if at least one of the following conditions is true for the current principal.
 * <ul>
 * <li>is the owner (as defined by the ACL).</li>
 * <li>holds the relevant system-wide {@link GrantedAuthority} injected into the constructor.</li>
 * <li>has {@link BasePermission#ADMINISTRATION} permission (as defined by the ACL).</li>
 * </ul>
 *
 * @author Ben Alex
 */
public class AclAuthorizationStrategyImpl implements AclAuthorizationStrategy {

    // ~ Instance fields
    // ================================================================================================

    private final GrantedAuthority gaGeneralChanges;

    private final GrantedAuthority gaModifyAuditing;

    private final GrantedAuthority gaTakeOwnership;

    private SidRetrievalStrategy sidRetrievalStrategy = new SidRetrievalStrategyImpl();

    // ~ Constructors
    // ===================================================================================================

    /**
     * Constructor. The only mandatory parameter relates to the system-wide {@link GrantedAuthority} instances that can
     * be held to always permit ACL changes.
     *
     * @param auths the <code>GrantedAuthority</code>s that have special permissions (index 0 is the authority needed to
     *            change ownership, index 1 is the authority needed to modify auditing details, index 2 is the authority
     *            needed to change other ACL and ACE details) (required)
     *            <p>
     *            Alternatively, a single value can be supplied for all three permissions.
     */
    public AclAuthorizationStrategyImpl(GrantedAuthority... auths) {
        Assert.isTrue(auths != null && (auths.length == 3 || auths.length == 1),
            "One or three GrantedAuthority instances required");
        if (auths.length == 3) {
            gaTakeOwnership = auths[0];
            gaModifyAuditing = auths[1];
            gaGeneralChanges = auths[2];
        }
        else {
            gaTakeOwnership = gaModifyAuditing = gaGeneralChanges = auths[0];
        }
    }

    public void securityCheck(Acl acl, int changeType) {
        if ((SecurityContextHolder.getContext() == null)
            || (SecurityContextHolder.getContext().getAuthentication() == null)
            || !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            throw new AccessDeniedException("Authenticated principal required to operate with ACLs");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authorized by virtual of ACL ownership
        Sid currentUser = createCurrentUser(authentication);
        if (currentUser.equals(acl.getOwner()) && ((changeType == CHANGE_GENERAL) || (changeType == CHANGE_OWNERSHIP))) {
            return;
        }

        // Not authorized by ACL ownership; try via adminstrative permissions
        GrantedAuthority requiredAuthority;
        if (changeType == CHANGE_AUDITING) {
            requiredAuthority = this.gaModifyAuditing;
        }
        else if (changeType == CHANGE_GENERAL) {
            requiredAuthority = this.gaGeneralChanges;
        }
        else if (changeType == CHANGE_OWNERSHIP) {
            requiredAuthority = this.gaTakeOwnership;
        }
        else {
            throw new IllegalArgumentException("Unknown change type");
        }

        // Iterate this principal's authorities to determine right
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (authorities.contains(requiredAuthority.getAuthority())) {
            return;
        }

        // Try to get permission via ACEs within the ACL
        List<Sid> sids = sidRetrievalStrategy.getSids(authentication);

        if (acl.isGranted(Arrays.asList(BasePermission.ADMINISTRATION), sids, false)) {
            return;
        }

        throw new AccessDeniedException(
            "Principal does not have required ACL permissions to perform requested operation");

    }

    /**
     * Creates a principal-like sid from the authentication information.
     *
     * @param authentication the authentication information that can provide principal and thus the sid's id will be
     *            dependant on the value inside
     * @return a sid with the ID taken from the authentication information
     */
    protected Sid createCurrentUser(Authentication authentication) {
        return new PrincipalSid(authentication);
    }

    public void setSidRetrievalStrategy(SidRetrievalStrategy sidRetrievalStrategy) {
        Assert.notNull(sidRetrievalStrategy, "SidRetrievalStrategy required");
        this.sidRetrievalStrategy = sidRetrievalStrategy;
    }

}