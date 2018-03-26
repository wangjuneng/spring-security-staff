package org.springframework.security.web.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;

/**
 * Declared in web.xml as
 *
 * <pre>
 * &lt;listener&gt;
 *     &lt;listener-class&gt;org.springframework.security.web.session.HttpSessionEventPublisher&lt;/listener-class&gt;
 * &lt;/listener&gt;
 * </pre>
 *
 * Publishes <code>HttpSessionApplicationEvent</code>s to the Spring Root WebApplicationContext. Maps
 * javax.servlet.http.HttpSessionListener.sessionCreated() to {@link HttpSessionCreatedEvent}. Maps
 * javax.servlet.http.HttpSessionListener.sessionDestroyed() to {@link HttpSessionDestroyedEvent}.
 *
 * @author Ray Krueger
 */
public class HttpSessionEventPublisher implements HttpSessionListener {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final String LOGGER_NAME = HttpSessionEventPublisher.class.getName();

    ApplicationContext getContext(ServletContext servletContext) {
        return SecurityWebApplicationContextUtils.findRequiredWebApplicationContext(servletContext);
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSessionCreatedEvent e = new HttpSessionCreatedEvent(event.getSession());

        Log log = LogFactory.getLog(LOGGER_NAME);

        if (log.isDebugEnabled()) {
            log.debug("Publishing event: " + e);
        }

        getContext(event.getSession().getServletContext()).publishEvent(e);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSessionDestroyedEvent e = new HttpSessionDestroyedEvent(event.getSession());
        Log log = LogFactory.getLog(LOGGER_NAME);

        if (log.isDebugEnabled()) {
            log.debug("Publishing event: " + e);
        }

        getContext(event.getSession().getServletContext()).publishEvent(e);
    }
}
