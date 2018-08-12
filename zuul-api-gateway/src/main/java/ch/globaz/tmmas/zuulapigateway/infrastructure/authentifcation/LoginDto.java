package ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;


/**
 * DTO représentant la requête de login
 */
@Getter
@Setter
@ToString
public class LoginDto {

    @NotBlank
    private String nomUtilisateur;

    @NotBlank
    private String motDePasse;

    public LoginDto(@NotBlank String nomUtilisateur, @NotBlank String motDePasse) {
        this.nomUtilisateur = nomUtilisateur;
        this.motDePasse = motDePasse;
    }
}
