package ch.globaz.tmmas.ldapserver.domain.model;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UtilisateursLdap {

	private String uid;
	private String password;

	public UtilisateursLdap(String uid, String password) {
		this.uid = uid;
		this.password = password;
	}

	UtilisateursLdap(){};
}
