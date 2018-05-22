package lab.application.security.exception;

import org.springframework.security.core.AuthenticationException;

public class AuthMethodNotSupportedException extends AuthenticationException {
    public AuthMethodNotSupportedException(String msg, Throwable t) {
        super(msg, t);
    }

    public AuthMethodNotSupportedException(String msg) {
        super(msg);
    }
}
