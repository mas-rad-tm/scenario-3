package ch.globaz.tmmas.personnesservice.application.security.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
//@AsyncListener
public class TentativeLoginListener implements ApplicationListener<AuditApplicationEvent> {

    //@Autowired
    //private ApplicationEventPublisher applicationEventPublisher;

    private static final Logger LOGGER = LoggerFactory.getLogger(TentativeLoginListener.class);




    @Override
    public void onApplicationEvent(AuditApplicationEvent auditApplicationEvent) {
        AuditEvent auditEvent = auditApplicationEvent.getAuditEvent();
        LOGGER.info("Principal " + auditEvent.getPrincipal() + " - " + auditEvent.getType());

        WebAuthenticationDetails details = (WebAuthenticationDetails) auditEvent.getData().get("details");
//        LOGGER.info("  Remote IP address: " + details.getRemoteAddress());
 //       LOGGER.info("  Session Id: " + details.getSessionId());
  //s      LOGGER.info("  Request URL: " + auditEvent.getData().get("requestUrl"));
    }


    public TentativeLoginListener() {
        super();
    }

/*
    public void onApplicationEvent(ApplicationEvent appEvent)
    {
        LOGGER.info("app event: {},{}", appEvent.toString(),appEvent.getClass().getTypeName());

        if (appEvent instanceof AuthenticationSuccessEvent)
        {
            LOGGER.info("app auth event: {}" + appEvent.toString());

            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) appEvent;

        }
    }
*/
}
