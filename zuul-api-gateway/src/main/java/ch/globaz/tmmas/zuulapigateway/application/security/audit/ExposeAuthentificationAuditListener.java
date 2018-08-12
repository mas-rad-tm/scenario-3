package ch.globaz.tmmas.zuulapigateway.application.security.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * Exposition des tentatives de connection à des fins d'audit
 */
@Component
public class ExposeAuthentificationAuditListener extends AbstractAuthenticationAuditListener {

    public static final String AUTHENTIFICATION_FAILURE
            = "AUTHENTIFICATION_FAILURE";

    public static final String AUTHENTIFICATION_SUCCESS
            = "AUTHENTIFICATION_SUCCESS";


    private final static Logger LOGGER = LoggerFactory.getLogger(ExposeAuthentificationAuditListener.class);

    @Override
    public void onApplicationEvent(AbstractAuthenticationEvent  event) {
        LOGGER.info("Event Type: {}",event);


        if (event instanceof AbstractAuthenticationFailureEvent) {
            onAuthentificationFailureEvent((AbstractAuthenticationFailureEvent) event);
        }

        if (event instanceof AuthenticationSuccessEvent) {
            onAuthentificationSucceesEvent((AuthenticationSuccessEvent) event);
        }

    }

    private void onAuthentificationSucceesEvent(AuthenticationSuccessEvent event) {

        LOGGER.info("{}",event);

        Map<String, Object> data = new HashMap<>();
        data.put("username", event.getAuthentication().getPrincipal());
        data.put("authorities", event.getAuthentication().getAuthorities());
        data.put("isauthenticated", event.getAuthentication().isAuthenticated() );
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTIFICATION_SUCCESS,
                data));
    }

    private void onAuthentificationFailureEvent(AbstractAuthenticationFailureEvent event) {

        LOGGER.info("{}",event);

        Map<String, Object> data = new HashMap<>();
        data.put("type", event.getException().getClass().getName());
        data.put("message", event.getException().getMessage());
        data.put("requestUrl", event.getSource() );
        if (event.getAuthentication().getDetails() != null) {
            data.put("details", event.getAuthentication().getDetails());
        }
        publish(new AuditEvent(event.getAuthentication().getName(), AUTHENTIFICATION_FAILURE,
                data));
    }

}
