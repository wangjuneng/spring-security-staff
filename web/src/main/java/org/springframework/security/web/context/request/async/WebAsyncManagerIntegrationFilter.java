package org.springframework.security.web.context.request.async;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.async.WebAsyncManager;
import org.springframework.web.context.request.async.WebAsyncUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class WebAsyncManagerIntegrationFilter extends OncePerRequestFilter {

    private static final Object CALLABLE_INTERCEPTOR_KEY = new Object();
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        WebAsyncManager asyncManager = WebAsyncUtils.getAsyncManager(request);
        
        SecurityContextCallableProcessingInterceptor securityProcessingInterceptor = (SecurityContextCallableProcessingInterceptor) asyncManager.getCallableInterceptor(CALLABLE_INTERCEPTOR_KEY);
        
        if (securityProcessingInterceptor == null) {
            asyncManager.registerCallableInterceptor(CALLABLE_INTERCEPTOR_KEY,
                    new SecurityContextCallableProcessingInterceptor());
        }
        
        filterChain.doFilter(request, response);
    }

}
