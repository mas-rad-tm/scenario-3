package ch.globaz.tmmas.ldapserver.application.api.exception;

import ch.globaz.tmmas.ldapserver.application.api.resource.ErrorResponseResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.ldap.AuthenticationException;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class RestAuthentificationExceptionHandler {

    private static final String AUTHENTIFICATION_ERROR = "L'authentification à échouée";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthentificationExceptionHandler.class);



    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex){

        LOGGER.error("Error during authentication, {}", ex.getMessage());

        ErrorResponseResource errors = new ErrorResponseResource(
                HttpStatus.UNAUTHORIZED,AUTHENTIFICATION_ERROR,
                "username/password error"
        );

        return ResponseEntity.status(errors.getStatus())
                .body(errors);
    }
}
