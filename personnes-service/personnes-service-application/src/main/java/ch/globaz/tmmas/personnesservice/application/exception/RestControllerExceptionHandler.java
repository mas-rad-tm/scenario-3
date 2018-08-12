package ch.globaz.tmmas.personnesservice.application.exception;


import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ErrorMessage;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ErrorResponseResource;
import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.LdapAuthentificationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe gérant les diverses exceptions pouvant être généré lors du traitement de la requête REST
 */
@ControllerAdvice
class RestControllerExceptionHandler extends ResponseEntityExceptionHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerExceptionHandler.class);


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final ErrorResponseResource errorResponseResource = new ErrorResponseResource(HttpStatus.BAD_REQUEST, ErrorMessage.MESSAGE_NOT_READABLE, ex.getMessage());

        return handleExceptionInternal(ex, errorResponseResource, headers, errorResponseResource.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final List<String> erreurs = new ArrayList<String>();

        ex.getBindingResult().getFieldErrors().forEach(erreur ->
                erreurs.add(erreur.getField() + ": " + erreur.getDefaultMessage()));

        ex.getBindingResult().getGlobalErrors().forEach(erreur ->
                erreurs.add(erreur.getObjectName() + ": " + erreur.getDefaultMessage()));

        final ErrorResponseResource errorResponseResource =
                new ErrorResponseResource(HttpStatus.BAD_REQUEST, ErrorMessage.METHOD_ARGUMENT_NOT_VALID, erreurs);

        return handleExceptionInternal(ex, errorResponseResource, headers, errorResponseResource.getStatus(), request);
    }


    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthentificationException(AuthenticationException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        final List<String> erreurs = new ArrayList<String>();

        final ErrorResponseResource errorResponseResource = new ErrorResponseResource(HttpStatus.BAD_REQUEST, ErrorMessage.AUTH_FAILED, erreurs);

        return handleExceptionInternal(ex, errorResponseResource, headers, errorResponseResource.getStatus(), request);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<Object> handleClientHttpErrorException(HttpClientErrorException ex){

        ErrorResponseResource errors =
                new ErrorResponseResource(HttpStatus.UNAUTHORIZED,ErrorMessage.REST_CLIENT_ERROR, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

    @ExceptionHandler(LdapAuthentificationException.class)
    protected ResponseEntity<Object> handleLdapAuthentificationException(LdapAuthentificationException ex){

        ErrorResponseResource errors =
                new ErrorResponseResource(HttpStatus.UNAUTHORIZED,ErrorMessage.LDAP_AUTH_EXCEPTION, ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
    }

}
