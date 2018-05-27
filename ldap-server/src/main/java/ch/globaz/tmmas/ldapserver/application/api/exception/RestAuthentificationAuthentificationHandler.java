package ch.globaz.tmmas.ldapserver.application.api.exception;

import ch.globaz.tmmas.ldapserver.application.api.resource.ErrorResponseResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestAuthentificationAuthentificationHandler {

    private static final String AUTHENTIFICATION_ERROR = "L'authentification à échouée";

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAdresseIncoherenceException(AuthenticationException ex){

        ErrorResponseResource errors = new ErrorResponseResource(HttpStatus.UNAUTHORIZED,AUTHENTIFICATION_ERROR,ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(errors);
    }
}
