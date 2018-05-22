package lab.application.api.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Point d'entrée par défaut pour la gestion des exception de type {@link AuthenticationException},
 * permet de fournit un point de sortie globale pour toutes les erreurs d'authentification
 */
@Component
public class RestAuthentificationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthentificationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        logger.error("Responding with unauthorized error. Message - {}", e.getMessage());

        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Sorry, You're not authorized to access this resource.");
    }
}
