package org.springframework.security.web.access;

import org.springframework.security.core.Authentication;

/**
 * Allows users to determine whether they have privileges for a given web URI.
 *
 * @author Luke Taylor
 * @since 3.0
 */
public interface WebInvocationPrivilegeEvaluator {

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt> object is allowed to invoke the
     * supplied URI.
     *
     * @param uri the URI excluding the context path (a default context path setting will be used)
     */
    public boolean isAllowed(String url, Authentication authentication);

    /**
     * Determines whether the user represented by the supplied <tt>Authentication</tt> object is allowed to invoke the
     * supplied URI, with the given .
     * <p>
     * Note the default implementation of <tt>FilterInvocationSecurityMetadataSource</tt> disregards the
     * <code>contextPath</code> when evaluating which secure object metadata applies to a given request URI, so
     * generally the <code>contextPath</code> is unimportant unless you are using a custom
     * <code>FilterInvocationSecurityMetadataSource</code>.
     *
     * @param uri the URI excluding the context path
     * @param contextPath the context path (may be null).
     * @param method the HTTP method (or null, for any method)
     * @param authentication the <tt>Authentication</tt> instance whose authorities should be used in evaluation whether
     *            access should be granted.
     * @return true if access is allowed, false if denied
     */
    public boolean isAllowed(String contextPath, String uri, String method, Authentication authentication);
}
