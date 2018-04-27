package ch.globaz.tmmas.personnesservice.domain.model;


import ch.globaz.tmmas.personnesservice.domain.common.ValueObject;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Localite, définie par:
 *
 * - nom: le nom de la localite
 * - npa, son npa unique
 */
@EqualsAndHashCode
@Getter
@ToString
public class Localite implements ValueObject<Localite> {

	private String nom;
	private Integer npa;

	public Localite(String nom, Integer npa){

		Preconditions.checkNotNull(nom,"Le nom est obligatoire");
		Preconditions.checkNotNull(npa,"Le npa est obligatoire");
		Preconditions.checkArgument(nom.length() > 0,"Le nom doit contenir une valeur non vide");

		this.npa = npa;
		this.nom = nom;
	}

	public boolean sameValueAs(Localite other) {
		return this.equals(other);
	}

	//Hibernate need
	Localite(){}
	private Long id;
}
