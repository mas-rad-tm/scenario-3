package ch.globaz.tmmas.personnesservice.application.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;


public class DistributiveEventMulticaster implements ApplicationEventMulticaster{


    @Autowired
    private  ApplicationEventMulticaster asyncApplicationEventMulticaster;

    @Autowired
    private  ApplicationEventMulticaster syncApplicationEventMulticaster;


    @Override
    public void addApplicationListener(ApplicationListener<?> applicationListener) {
        // choose multicaster by annotation
        if (applicationListener.getClass().getAnnotation(AsyncListener.class) != null) {
            asyncApplicationEventMulticaster.addApplicationListener(applicationListener);
        } else {
            syncApplicationEventMulticaster.addApplicationListener(applicationListener);
        }
    }

    @Override
    public void addApplicationListenerBean(String s) {

    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> applicationListener) {
        asyncApplicationEventMulticaster.removeApplicationListener(applicationListener);
        syncApplicationEventMulticaster.removeApplicationListener(applicationListener);
    }

    @Override
    public void removeApplicationListenerBean(String s) {

    }

    @Override
    public void removeAllListeners() {
        asyncApplicationEventMulticaster.removeAllListeners();
        syncApplicationEventMulticaster.removeAllListeners();
    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent) {
        asyncApplicationEventMulticaster.multicastEvent(applicationEvent);
        syncApplicationEventMulticaster.multicastEvent(applicationEvent);

    }

    @Override
    public void multicastEvent(ApplicationEvent applicationEvent, ResolvableType resolvableType) {
        asyncApplicationEventMulticaster.multicastEvent(applicationEvent);
        syncApplicationEventMulticaster.multicastEvent(applicationEvent);

    }


}
