package ch.globaz.tmmas.authentificationservice.application.exception;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class JwtExpirationException extends RuntimeException {
    public JwtExpirationException(String message) {
        super(message);
    }
}
