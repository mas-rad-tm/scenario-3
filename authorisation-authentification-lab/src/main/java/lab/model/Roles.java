package lab.model;

public enum Roles {

    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    public final String nom;

    Roles(String nom){
        this.nom = nom;
    }

}
