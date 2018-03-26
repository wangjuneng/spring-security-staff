package org.springframework.security.web.access;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;

/**
 * An {@link AccessDeniedHandler} that delegates to other {@link AccessDeniedHandler} instances based upon the type of
 * {@link AccessDeniedException} passed into
 * {@link #handle(HttpServletRequest, HttpServletResponse, AccessDeniedException)}.
 *
 * @author Rob Winch
 * @since 3.2
 */
public class DelegatingAccessDeniedHandler implements AccessDeniedHandler {

    private final LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers;

    private final AccessDeniedHandler defaultHandler;

    /**
     * Creates a new instance
     *
     * @param handlers a map of the {@link AccessDeniedException} class to the {@link AccessDeniedHandler} that should
     *            be used. Each is considered in the order they are specified and only the first
     *            {@link AccessDeniedHandler} is ued.
     * @param defaultHandler the default {@link AccessDeniedHandler} that should be used if none of the handlers
     *            matches.
     */
    public DelegatingAccessDeniedHandler(
        LinkedHashMap<Class<? extends AccessDeniedException>, AccessDeniedHandler> handlers,
        AccessDeniedHandler defaultHandler) {
        Assert.notEmpty(handlers, "handlers cannot be null or empty");
        Assert.notNull(defaultHandler, "defaultHandler cannot be null");
        this.handlers = handlers;
        this.defaultHandler = defaultHandler;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        for (Entry<Class<? extends AccessDeniedException>, AccessDeniedHandler> entry : handlers.entrySet()) {
            Class<? extends AccessDeniedException> handlerClass = entry.getKey();
            if (handlerClass.isAssignableFrom(accessDeniedException.getClass())) {
                AccessDeniedHandler handler = entry.getValue();
                handler.handle(request, response, accessDeniedException);
                return;
            }
        }
        defaultHandler.handle(request, response, accessDeniedException);

    }

}
