package ch.globaz.tmmas.personnesservice.infrastructure.authentifcation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UtilisateurLdapDto {

    private String uid;
    private String nom;
    private String prenom;
    private List<String> roles;

    public UtilisateurLdapDto(String uid, String nom, String prenom, List<String> roles) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
    }

    UtilisateurLdapDto(){};
}
