package ch.globaz.tmmas.zuulapigateway.application.dto.dto.request;

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
public class LoginRequest {

    @NotBlank
    private String nomUtilisateur;

    @NotBlank
    private String motDePasse;

}
