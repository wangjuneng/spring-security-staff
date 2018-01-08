package org.springframework.security.authentication.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.util.ClassUtils;

public class LoggerListener implements ApplicationListener<AbstractAuthenticationEvent> {
    // ~ Static fields/initializers
    // =====================================================================================

    private static final Log logger = LogFactory.getLog(LoggerListener.class);

    /**
     * If set to true, {@link InteractiveAuthenticationSuccessEvent} will be logged (defaults to true)
     */
    private boolean logInteractiveAuthenticationSuccessEvents = true;

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
        if (!logInteractiveAuthenticationSuccessEvents && event instanceof InteractiveAuthenticationSuccessEvent) {
            return;
        }
        
        if(logger.isWarnEnabled()){
            if (logger.isWarnEnabled()) {
                final StringBuilder builder = new StringBuilder();
                builder.append("Authentication event ");
                builder.append(ClassUtils.getShortName(event.getClass()));
                builder.append(": ");
                builder.append(event.getAuthentication().getName());
                builder.append("; details: ");
                builder.append(event.getAuthentication().getDetails());

                if (event instanceof AbstractAuthenticationFailureEvent) {
                    builder.append("; exception: ");
                    builder.append(((AbstractAuthenticationFailureEvent) event)
                            .getException().getMessage());
                }

                logger.warn(builder.toString());
            }
        }

    }
    

    public boolean isLogInteractiveAuthenticationSuccessEvents() {
        return logInteractiveAuthenticationSuccessEvents;
    }

    public void setLogInteractiveAuthenticationSuccessEvents(
            boolean logInteractiveAuthenticationSuccessEvents) {
        this.logInteractiveAuthenticationSuccessEvents = logInteractiveAuthenticationSuccessEvents;
    }

}
