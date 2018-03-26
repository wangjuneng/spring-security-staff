package org.springframework.security.web.authentication.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;

/**
 * Implementation of the {@link LogoutSuccessHandler}. By default returns an HTTP status code of {@code 200}. This is
 * useful in REST-type scenarios where a redirect upon a successful logout is not desired.
 *
 * @author Gunnar Hillert
 * @since 4.0.2
 */
public class HttpStatusReturningLogoutSuccessHandler implements LogoutSuccessHandler {

    private final HttpStatus httpStatusToReturn;

    /**
     * Initialize the {@code HttpStatusLogoutSuccessHandler} with a user-defined {@link HttpStatus}.
     *
     * @param httpStatusToReturn Must not be {@code null}.
     */
    public HttpStatusReturningLogoutSuccessHandler(HttpStatus httpStatusToReturn) {
        Assert.notNull(httpStatusToReturn, "The provided HttpStatus must not be null.");
        this.httpStatusToReturn = httpStatusToReturn;
    }
    
    /**
     * Initialize the {@code HttpStatusLogoutSuccessHandler} with the default
     * {@link HttpStatus#OK}.
     */
    public HttpStatusReturningLogoutSuccessHandler() {
        this.httpStatusToReturn = HttpStatus.OK;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws IOException, ServletException {
        response.setStatus(this.httpStatusToReturn.value());
        response.getWriter().flush();

    }

}
