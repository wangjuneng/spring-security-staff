package org.springframework.security.web.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

public class SimpleRedirectInvalidSessionStrategy implements InvalidSessionStrategy {

    private final Log logger = LogFactory.getLog(getClass());

    private final String destinationUrl;

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private boolean createNewSession = true;

    public SimpleRedirectInvalidSessionStrategy(String invalidSessionUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
        this.destinationUrl = invalidSessionUrl;
    }

    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException,
        ServletException {
        logger.debug("Starting new session (if required) and redirecting to '" + destinationUrl + "'");
        if (createNewSession) {
            request.getSession();
        }
        redirectStrategy.sendRedirect(request, response, destinationUrl);
    }

    /**
     * Determines whether a new session should be created before redirecting (to avoid possible looping issues where the
     * same session ID is sent with the redirected request). Alternatively, ensure that the configured URL does not pass
     * through the {@code SessionManagementFilter}.
     *
     * @param createNewSession defaults to {@code true}.
     */
    public void setCreateNewSession(boolean createNewSession) {
        this.createNewSession = createNewSession;
    }

}
