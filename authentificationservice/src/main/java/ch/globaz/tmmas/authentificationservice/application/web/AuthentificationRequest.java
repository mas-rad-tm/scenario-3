package ch.globaz.tmmas.authentificationservice.application.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AuthentificationRequest {

	private String nomUtilisateur;
	private String motDePasse;

	public AuthentificationRequest(String nomUtilisateur, String motDePasse) {
		this.nomUtilisateur = nomUtilisateur;
		this.motDePasse = motDePasse;
	}

	public AuthentificationRequest() {
	}
}
