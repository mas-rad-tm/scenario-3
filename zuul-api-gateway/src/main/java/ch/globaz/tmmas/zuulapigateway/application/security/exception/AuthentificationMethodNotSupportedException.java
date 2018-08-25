package ch.globaz.tmmas.zuulapigateway.application.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthentificationMethodNotSupportedException extends AuthenticationException {
    public AuthentificationMethodNotSupportedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthentificationMethodNotSupportedException(String msg) {
        super(msg);
    }
}
