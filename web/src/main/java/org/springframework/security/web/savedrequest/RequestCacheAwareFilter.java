package org.springframework.security.web.savedrequest;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

public class RequestCacheAwareFilter extends GenericFilterBean {

    private RequestCache requestCache;

    public RequestCacheAwareFilter() {
        this(new HttpSessionRequestCache());
    }

    public RequestCacheAwareFilter(RequestCache requestCache) {
        Assert.notNull(requestCache, "requestCache cannot be null");
        this.requestCache = requestCache;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
        ServletException {
        HttpServletRequest wrappedSavedRequest = requestCache.getMatchingReqeust((HttpServletRequest) request,
            (HttpServletResponse) response);
        chain.doFilter(wrappedSavedRequest == null ? request : wrappedSavedRequest, response);
    }

}
