package ch.globaz.tmmas.personnesservice.application.api.web.resources.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Ressources utilisées pour toutes les exceptions générés au niveau des requêtes REST
 */
@Getter
public class ErrorResponseResource {

	private final HttpStatus status;
	private final String message;
	private final Date timestamp;
	private ErrorCode errorCode;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> erreurs;

	public ErrorResponseResource(HttpStatus status, ErrorMessage message, List<String> erreurs) {
		this.erreurs = erreurs;
		this.message = message.libelle;
		this.status = status;
		this.timestamp = new Date();
	}

	public ErrorResponseResource(HttpStatus status, ErrorMessage message, String erreur, ErrorCode errorCode) {
		this(status,message, Arrays.asList(erreur));
		this.errorCode = errorCode;
	}

	public ErrorResponseResource(HttpStatus status, ErrorMessage message, String erreur) {
		this(status,message, Arrays.asList(erreur));
	}

	public ErrorResponseResource(HttpStatus status, ErrorMessage message) {
		this.message = message.libelle;
		this.status = status;
		this.timestamp = new Date();
	}

	public ErrorResponseResource(HttpStatus status, String s) {
		this.message = s;
		this.status = status;
		this.timestamp = new Date();
	}
}
