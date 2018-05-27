package ch.globaz.tmmas.personnesservice.application.api.web.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

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
