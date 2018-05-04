package ch.globaz.tmmas.authentificationservice.application.exception;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class MalformedJwtException extends RuntimeException {
    public MalformedJwtException(String message) {
        super(message);
    }
}
