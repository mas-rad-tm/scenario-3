package ch.globaz.tmmas.authentificationservice.domain.model;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
