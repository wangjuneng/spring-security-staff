package org.springframework.security.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Indicates a class can process a specific
 * {@link org.springframework.security.core.Authentication} implementation.
 *
 * @author Ben Alex
 */
public interface AuthenticationProvider {
    // ~ Methods
    // ========================================================================================================

    /**
     * Performs authentication with the same contract as
     * {@link org.springframework.security.authentication.AuthenticationManager#authenticate(Authentication)} .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return <code>null</code> if the
     *         <code>AuthenticationProvider</code> is unable to support authentication of the passed
     *         <code>Authentication</code> object. In such a case, the next <code>AuthenticationProvider</code> that
     *         supports the presented <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    Authentication authenticate(Authentication authentication) throws AuthenticationException;

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the indicated
     * <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an <code>AuthenticationProvider</code> will be able to
     * authenticate the presented instance of the <code>Authentication</code> class. It simply indicates it can support
     * closer evaluation of it. An <code>AuthenticationProvider</code> can still return <code>null</code> from the
     * {@link #authenticate(Authentication)} method to indicate another <code>AuthenticationProvider</code> should be
     * tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing authentication is conducted at runtime
     * the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication
     * @return <code>true</code> if the implementation can more closely evaluate the <code>Authentication</code> class
     *         presented
     */
    boolean supports(Class<?> authentication);
}
