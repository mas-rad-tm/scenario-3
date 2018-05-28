package lab.model;

public enum Permissions {

    CREATE_PERSONNE("P_CREATE_PERSONNES"),
    READ_PERSONNE("P_READ_PERSONNES"),
    CREATE_ADRESSE("P_CREATE_ADRESSES"),
    READ_ADRESSE("P_READ_ADRESSES");

    private String nom;

    Permissions(String nom){
        this.nom = nom;
    }

    public String nom(){
        return nom;
    }
}
