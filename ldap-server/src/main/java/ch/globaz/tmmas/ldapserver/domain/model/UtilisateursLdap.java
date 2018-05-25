package ch.globaz.tmmas.ldapserver.domain.model;


import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UtilisateursLdap {

	private String uid;
	private String nom;
	private String prenom;
	private List<String> roles;

	public UtilisateursLdap(String uid, String nom, String prenom, List<String> roles) {
		this.uid = uid;
		this.nom = nom;
		this.prenom = prenom;
		this.roles = roles;
	}

	UtilisateursLdap(){};
}
