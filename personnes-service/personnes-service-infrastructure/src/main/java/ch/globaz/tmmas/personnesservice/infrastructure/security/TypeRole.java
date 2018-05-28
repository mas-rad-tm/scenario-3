package ch.globaz.tmmas.personnesservice.infrastructure.security;

public enum TypeRole {

    RESPONSABLE("ROLE_RESPONSABLE"),
    GESTIONNAIRE("ROLE_GESTIONNAIRE"),
    STAGIAIRE("ROLE_STAGIAIRE");

    private String nom;

    TypeRole(String nom){
        this.nom = nom;
    }

    public String nom(){
        return nom;
    }
}
