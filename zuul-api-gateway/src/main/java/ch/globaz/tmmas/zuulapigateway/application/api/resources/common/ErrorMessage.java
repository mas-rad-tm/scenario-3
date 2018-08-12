package ch.globaz.tmmas.zuulapigateway.application.api.resources.common;

public enum ErrorMessage {

    //technique
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password"),
    JWT_TOKEN_EXPIRED("JWT Token has expired"),
    AUTH_METHOD_NOT_SUPPORTED("Authentification method not supported"),
    AUTH_FAILED("Authentification failed"),
    REST_CLIENT_ERROR("Rest client error"),
    LDAP_AUTH_EXCEPTION("LDAP Authentification exception"),
    MESSAGE_NOT_READABLE("Message not readable exception"),

    //métier
    INCOHERENCE_PERSONNE_MSG("Incohérence métier pour la personne"),
    INCOHERENCE_ADRESSE_MSG("Incohérence métier pour l'adresse"),
    METHOD_ARGUMENT_NOT_VALID("Method argument(s) not valid"),
    NO_PERSON_FOUND_WITH_ID("No personne found with id: %s");




    public String libelle;

    ErrorMessage(String libelle){
        this.libelle = libelle;
    }
}
