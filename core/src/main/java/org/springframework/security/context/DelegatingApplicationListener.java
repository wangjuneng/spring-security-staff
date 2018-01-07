package org.springframework.security.context;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.util.Assert;

public class DelegatingApplicationListener implements ApplicationListener<ApplicationEvent> {

    private List<SmartApplicationListener> listeners = new CopyOnWriteArrayList<SmartApplicationListener>();

    /**
     * Adds a new SmartApplicationListener to use.
     *
     * @param smartApplicationListener the SmartApplicationListener to use. Cannot be null.
     */
    public void addListener(SmartApplicationListener smartApplicationListener) {
        Assert.notNull(smartApplicationListener, "smartApplicationListener cannot be null");
        listeners.add(smartApplicationListener);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event == null) {
            return;
        }

        for (SmartApplicationListener listener : listeners) {
            Object source = event.getSource();

            if (source != null && listener.supportsEventType(event.getClass())
                && listener.supportsSourceType(source.getClass())) {
                listener.onApplicationEvent(event);
            }
        }

    }
}
