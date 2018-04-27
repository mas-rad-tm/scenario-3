package ch.globaz.tmmas.personnesservice.application.api.web.resources;

import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResourceAttributes;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ResourceObject;
import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.DateFormatter;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.Localite;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


/**
 * Ressources REST pour les dossiers
 */
@Getter
public class AdresseResourceAttributes implements ResourceAttributes {


	private Localite localite;

	private String rue;
	private Integer numero;
	private String complement;

	@JsonProperty("id")
	private Long technicalId;

	private String formatedDateDebutValidite;
	private String isoDateDebutValidite;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String formatedDateFinValidite;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String isoDateFinValidite;



	private Boolean isActive;

	public AdresseResourceAttributes(){}


	public AdresseResourceAttributes(Adresse adresse){


		this.formatedDateDebutValidite = adresse.getDateDebutValidite().format(DateFormatter.DATE_FORMAT);
		this.isoDateDebutValidite = adresse.getDateDebutValidite().toString();

		if(adresse.getDateFinValidite() != null){
			this.formatedDateFinValidite = adresse.getDateFinValidite().format(DateFormatter.DATE_FORMAT);
			this.isoDateFinValidite = adresse.getDateFinValidite().toString();
		}



		this.technicalId = adresse.getId();
		this.rue = adresse.getRue();
		this.isActive= adresse.getIsActive();
		this.numero = adresse.getNumero();
		this.complement = adresse.getComplement();
		this.localite = adresse.getLocalite();

	}

	public ResourceObject buildResourceObject () {
	    return new ResourceObject(this.getTechnicalId(),"adresse",this);
    }

}
