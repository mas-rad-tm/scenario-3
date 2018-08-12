package ch.globaz.tmmas.zuulapigateway.application.api.resources.common;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Codes d'erreur propre à l'application
 */
public enum ErrorCode {
    GLOBAL(2),
    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);

    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

}
