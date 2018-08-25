package ch.globaz.tmmas.zuulapigateway.application.security.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthorizationAuditListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static ch.globaz.tmmas.zuulapigateway.application.security.audit.ExposeAuthentificationAuditListener.AUTHENTIFICATION_FAILURE;

@Component
public class ExposeAuthorizationAuditListener extends AbstractAuthorizationAuditListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExposeAuthorizationAuditListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {

        LOGGER.info("Event Type: {}",event);


        if(event instanceof AuthorizationFailureEvent){
            onAuthorizationFailureEvent((AuthorizationFailureEvent)event);
        }
    }

    private void onAuthorizationFailureEvent(AuthorizationFailureEvent event) {

        LOGGER.info("#onAuthorizationFailureEvent {}",event.getSource().toString());

        Map<String, Object> data = new HashMap<>();
        data.put("type", event.getAccessDeniedException().getClass().getName());
        data.put("message", event.getAccessDeniedException().getMessage());
        data.put("requestUrl", event.getSource() );

        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTIFICATION_FAILURE,
                data));
    }
}
