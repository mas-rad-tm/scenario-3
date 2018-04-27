package ch.globaz.tmmas.personnesservice.application.api.web.resources;

import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResourceAttributes;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResourceObject;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.DateFormatter;
import ch.globaz.tmmas.personnesservice.domain.model.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 * Ressources REST pour les dossiers
 */
@Getter
public class PersonneMoraleResourceAttributes implements ResourceAttributes {



	private Sexe sexe;
	private String nom;
	private String prenom;
	private String nss;

	@JsonProperty("id")
	private Long technicalId;

	private String formattedDateNaissance;
	private String isoDateNaissance;



	public PersonneMoraleResourceAttributes(){}


	public PersonneMoraleResourceAttributes(PersonneMorale personneMorale){


		this.formattedDateNaissance = personneMorale.getDateNaissance().format(DateFormatter.DATE_FORMAT);
		this.isoDateNaissance = personneMorale.getDateNaissance().toString();
		this.technicalId = personneMorale.getId();
		this.nom = personneMorale.getNom();
		this.prenom = personneMorale.getPrenom();
		this.sexe = personneMorale.getSexe();
		this.nss = personneMorale.nssAsString();

	}

	public ResourceObject buildResourceObject () {
	    return new ResourceObject(this.getTechnicalId(),"personneMorale",
			    this);
    }

}
