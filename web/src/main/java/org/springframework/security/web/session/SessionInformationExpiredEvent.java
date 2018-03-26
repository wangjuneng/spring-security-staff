package org.springframework.security.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.util.Assert;

public final class SessionInformationExpiredEvent extends ApplicationEvent {
    private HttpServletRequest request;

    private HttpServletResponse response;

    /**
     * Creates a new instance
     *
     * @param sessionInformation the SessionInformation that is expired
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     */
    public SessionInformationExpiredEvent(SessionInformation sessionInformation, HttpServletRequest request,
        HttpServletResponse response) {
        super(sessionInformation);
        Assert.notNull(request, "request cannot be null");
        Assert.notNull(response, "response cannot be null");
        this.request = request;
        this.response = response;
    }

    /**
     * @return the request
     */
    public HttpServletRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    public HttpServletResponse getResponse() {
        return response;
    }

    public SessionInformation getSessionInformation() {
        return (SessionInformation) getSource();
    }
}
