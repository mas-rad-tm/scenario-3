package ch.globaz.tmmas.personnesservice.domain.model;


import ch.globaz.tmmas.personnesservice.domain.common.ValueObject;
import lombok.Getter;

import java.time.Period;
import java.time.ZonedDateTime;

@Getter
public class Adresse implements ValueObject<Adresse> {

	private Localite localite;
	private String rue;
	private Integer numero;
	private String complement;
	private ZonedDateTime dateDebutValidite;
	private ZonedDateTime dateFinValidite;
	private Boolean isActive;
	private PersonneMorale personneMorale;

	public Adresse(Localite localite, PersonneMorale personne, String rue, Integer numero, String complement, ZonedDateTime
			dateValidite) {
		this.localite = localite;
		this.rue = rue;
		this.complement = complement;
		this.dateDebutValidite = dateValidite;
		this.numero = numero;
		this.isActive = Boolean.TRUE;
		this.personneMorale = personne;
	}


	public Adresse desactive(ZonedDateTime dateFinvalidite){
		this.isActive = Boolean.FALSE;
		this.dateFinValidite = dateFinvalidite.minus(Period.ofDays(1));
		return this;
	}
	@Override
	public boolean sameValueAs(Adresse other) {
		return false;
	}

	private Long id;

	Adresse(){}
}
