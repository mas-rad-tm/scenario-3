package ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation;

public class LdapAuthentificationException extends RuntimeException {

    public LdapAuthentificationException(String message){
        super(message);
    }
}
