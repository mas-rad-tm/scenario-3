package ch.globaz.tmmas.personnesservice.infrastructure.authentifcation;

public class LdapAuthentificationException extends RuntimeException {

    public LdapAuthentificationException(String message){
        super(message);
    }
}
