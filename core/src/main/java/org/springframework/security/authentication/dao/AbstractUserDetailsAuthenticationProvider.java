package org.springframework.security.authentication.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * A base {@link AuthenticationProvider} that allows subclasses to override and work with
 * {@link org.springframework.security.core.userdetails.UserDetails} objects. The class is designed to respond to
 * {@link UsernamePasswordAuthenticationToken} authentication requests.
 * <p>
 * Upon successful validation, a <code>UsernamePasswordAuthenticationToken</code> will be created and returned to the
 * caller. The token will include as its principal either a <code>String</code> representation of the username, or the
 * {@link UserDetails} that was returned from the authentication repository. Using <code>String</code> is appropriate if
 * a container adapter is being used, as it expects <code>String</code> representations of the username. Using
 * <code>UserDetails</code> is appropriate if you require access to additional properties of the authenticated user,
 * such as email addresses, human-friendly names etc. As container adapters are not recommended to be used, and
 * <code>UserDetails</code> implementations provide additional flexibility, by default a <code>UserDetails</code> is
 * returned. To override this default, set the {@link #setForcePrincipalAsString} to <code>true</code>.
 * <p>
 * Caching is handled by storing the <code>UserDetails</code> object being placed in the {@link UserCache}. This ensures
 * that subsequent requests with the same username can be validated without needing to query the
 * {@link UserDetailsService}. It should be noted that if a user appears to present an incorrect password, the
 * {@link UserDetailsService} will be queried to confirm the most up-to-date password was used for comparison. Caching
 * is only likely to be required for stateless applications. In a normal web application, for example, the
 * <tt>SecurityContext</tt> is stored in the user's session and the user isn't reauthenticated on each request. The
 * default cache implementation is therefore {@link NullUserCache}.
 *
 * @author Ben Alex
 */
public class AbstractUserDetailsAuthenticationProvider implements AuthenticationProvider, InitializingBean,
    MessageSourceAware {
    protected final Log logger = LogFactory.getLog(getClass());

    // ~ Instance fields
    // ================================================================================================

}
