package org.springframework.security.core.userdetails;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.GrantedAuthority;

/**
 * Provides core user information.
 * <p>
 * Implementations are not used directly by Spring Security for security purposes. They simply store user information
 * which is later encapsulated into {@link Authentication} objects. This allows non-security related user information
 * (such as email addresses, telephone numbers etc) to be stored in a convenient location.
 * <p>
 * Concrete implementations must take particular care to ensure the non-null contract detailed for each method is
 * enforced. See {@link org.springframework.security.core.userdetails.User} for a reference implementation (which you
 * might like to extend or use in your code).
 *
 * @see UserDetailsService
 * @see UserCache
 * @author Ben Alex
 */
public interface UserDetails extends AuthenticatedPrincipal, Serializable {

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    Collection<? extends GrantedAuthority> getAuthorities();

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    String getPassword();

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    String getUsername();

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired), <code>false</code> if no longer valid
     *         (ie expired)
     */
    boolean isAccountNonExpired();

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    boolean isAccountNonLocked();

    /**
     * Indicates whether the user's credentials (password) has expired. Expired credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired), <code>false</code> if no longer
     *         valid (ie expired)
     */
    boolean isCredentialsNonExpired();

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    boolean isEnabled();

    /**
     * Returns the name of the user. Cannot return <code>null</code>. The default implementation of this method returns
     * {@link #getUsername()}.
     *
     * @return the name of the user (never <code>null</code>)
     */
    default String getName() {
        return getUsername();
    }
}
