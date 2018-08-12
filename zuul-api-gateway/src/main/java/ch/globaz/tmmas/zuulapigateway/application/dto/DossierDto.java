package ch.globaz.tmmas.zuulapigateway.application.dto;


import ch.globaz.tmmas.zuulapigateway.application.dto.localdate.LocalDateDeserializer;
import ch.globaz.tmmas.zuulapigateway.application.dto.localdate.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ToString
public class DossierDto {

	private String numero;
	private Long requerantId;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate dateEnregistrement;


	public DossierDto(){}


	private DossierDto(String numero, Long requerantId, String dateEnregistrement){

		this.numero = numero;
		this.requerantId = requerantId;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

		this.dateEnregistrement = LocalDate.parse(dateEnregistrement,formatter);
	}


	public String getNumero() {
		return numero;
	}

	public LocalDate getDateEnregistrement() {
		return dateEnregistrement;
	}

	public Long getRequerantId () {
		return requerantId;
	}

	public static DossierDto from(String numero, Long requerantId, String dateEnregistrement) {

		return new DossierDto(numero,requerantId,dateEnregistrement);

	}


}
