package ch.globaz.tmmas.zuulapigateway.application.dto;



import ch.globaz.tmmas.zuulapigateway.application.dto.localdate.LocalDateDeserializer;
import ch.globaz.tmmas.zuulapigateway.application.dto.localdate.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
public class PersonnesPhysiqueDto {

	private String nom;
	private String prenom;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateNaissance;

	private String nss;

	private Long id;


	public PersonnesPhysiqueDto(){}


	private PersonnesPhysiqueDto(Long id, String nom, String prenom, String dateNaissance, String nss){

		this.nom = nom;
		this.prenom = prenom;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.dateNaissance = LocalDate.parse(dateNaissance,formatter);
		this.nss = nss;
		this.id = id;
	}


	public String getNom() {
		return nom;
	}

	public LocalDate getDateNaissance() {
		return dateNaissance;
	}

	public String getPrenom () {
		return prenom;
	}

	public String getNss () {
		return nss;
	}

	public Long getId(){
		return id;
	}


}
