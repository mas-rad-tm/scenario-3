package ch.globaz.tmmas.personnesservice.domain.model;

import ch.globaz.tmmas.personnesservice.domain.common.Entity;
import com.google.common.base.Preconditions;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter()
@EqualsAndHashCode(of = "nss")
@ToString(exclude = "adresses")
public class PersonneMorale implements Entity<PersonneMorale>{


	private NSS nss;
	private String nom;
	private String prenom;
	private ZonedDateTime dateNaissance;
	private Sexe sexe;
	@Setter
	private Adresse adresseActive;

	public PersonneMorale(NSS nss, String nom, String prenom, ZonedDateTime dateNaissance, Sexe sexe){
		Preconditions.checkNotNull(nss,"Le nss est obligatoire");
		Preconditions.checkNotNull(nom,"Le nom est obligatoire");
		Preconditions.checkNotNull(prenom,"Le prenom est obligatoire");
		Preconditions.checkNotNull(dateNaissance,"La dateDeNaissance est obligatoire");
		Preconditions.checkNotNull(sexe,"Le sexe est obligatoire");

		this.nss = nss;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.sexe = sexe;
	}


	public String nssAsString(){
		return this.getNss().getNss();
	}


	@Override
	public boolean sameIdentityAs(PersonneMorale other) {

		return this.nss.equals(other.nss);
	}


	public boolean hasAdressesActiveDefinies(){
		return this.adresseActive != null;
	}

	//hibernate needs
	PersonneMorale(){};
	private Long id;
}
