package org.springframework.security.web.access;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;

/**
 * Base implementation of {@link AccessDeniedHandler}.
 * <p>
 * This implementation sends a 403 (SC_FORBIDDEN) HTTP error code. In addition, if an {@link #errorPage} is defined, the
 * implementation will perform a request dispatcher "forward" to the specified error page view. Being a "forward", the
 * <code>SecurityContextHolder</code> will remain populated. This is of benefit if the view (or a tag library or macro)
 * wishes to access the <code>SecurityContextHolder</code>. The request scope will also be populated with the exception
 * itself, available from the key {@link WebAttributes#ACCESS_DENIED_403}.
 *
 * @author Ben Alex
 */
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    // ~ Static fields/initializers
    // =====================================================================================

    protected static final Log logger = LogFactory.getLog(AccessDeniedHandlerImpl.class);

    // ~ Instance fields
    // ================================================================================================

    private String errorPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (!response.isCommitted()) {
            if (errorPage != null) {
                request.setAttribute(WebAttributes.ACCESS_DENIED_403, accessDeniedException);

                // set 403
                response.setStatus(HttpStatus.FORBIDDEN.value());

                // forward to error page
                RequestDispatcher dispatcher = request.getRequestDispatcher(errorPage);
                dispatcher.forward(request, response);

            }
            else {
                response.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
            }
        }

    }

    /**
     * The error page to use. Must begin with a "/" and is interpreted relative to the current context root.
     *
     * @param errorPage the dispatcher path to display
     * @throws IllegalArgumentException if the argument doesn't comply with the above limitations
     */
    public void setErrorPage(String errorPage) {
        if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
        }

        this.errorPage = errorPage;
    }

}
