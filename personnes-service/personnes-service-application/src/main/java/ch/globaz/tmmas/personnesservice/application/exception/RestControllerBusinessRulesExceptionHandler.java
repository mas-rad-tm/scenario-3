package ch.globaz.tmmas.personnesservice.application.exception;

import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ErrorResponseResource;
import ch.globaz.tmmas.personnesservice.domain.exception.AdresseIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.exception.PersonnesIncoherenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Classe gérant les diverses exceptions pouvant être généré lors du traitement de la requête REST
 */
@ControllerAdvice
class RestControllerBusinessRulesExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestControllerBusinessRulesExceptionHandler.class);


    private static final String INCOHERENCE_PERSONNE_MSG = "Incohérence métier pour la personne";
    private static final String INCOHERENCE_ADRESSE_MSG = "Incohérence métier pour l'adresse";



    @ExceptionHandler(AdresseIncoherenceException.class)
    protected ResponseEntity<Object> handleAdresseIncoherenceException(AdresseIncoherenceException ex){

        ErrorResponseResource errors = new ErrorResponseResource(HttpStatus.CONFLICT,INCOHERENCE_ADRESSE_MSG,ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errors);
    }

    @ExceptionHandler(PersonnesIncoherenceException.class)
    protected ResponseEntity<Object> handleAdresseIncoherenceException(PersonnesIncoherenceException ex){

        ErrorResponseResource errors = new ErrorResponseResource(HttpStatus.CONFLICT,INCOHERENCE_PERSONNE_MSG,ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errors);
    }

}
