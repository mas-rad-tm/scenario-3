package ch.globaz.tmmas.personnesservice.application.security.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Surgcharge du fonctionnement de base de Spring, qui retourne la représentation de l'erreur
 * sous forme de page html.
 */
@Component
public class RestAuthentificationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthentificationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        logger.error("Erreur non autorisé. Message - {}", e.getMessage());

        //401 non autorisé
         httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(),
                    "Accès à la resource non autorisé.");

    }
}
