package ch.globaz.tmmas.authentificationservice.application.exception;

/**
 * Created by seb on .
 * <p>
 * ${VERSION}
 */
public class JwtBadSignatureException extends RuntimeException {
    public JwtBadSignatureException(String message) {
        super(message);
    }
}
