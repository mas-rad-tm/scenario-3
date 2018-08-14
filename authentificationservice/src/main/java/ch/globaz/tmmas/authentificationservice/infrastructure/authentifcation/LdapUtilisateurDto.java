package ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class LdapUtilisateurDto {

    private String uid;
    private String nom;
    private String prenom;
    private List<String> roles;

    public LdapUtilisateurDto(String uid, String nom, String prenom, List<String> roles) {
        this.uid = uid;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
    }

    LdapUtilisateurDto(){};
}
