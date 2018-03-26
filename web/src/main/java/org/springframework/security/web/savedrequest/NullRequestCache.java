package org.springframework.security.web.savedrequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NullRequestCache implements RequestCache {

    @Override
    public void saveRequest(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public SavedRequest getRequest(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public HttpServletRequest getMatchingReqeust(HttpServletRequest request, HttpServletResponse resposne) {
        return null;
    }

    @Override
    public void removeRequest(HttpServletRequest request, HttpServletResponse response) {

    }

}
