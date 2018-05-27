package ch.globaz.tmmas.personnesservice.application.api.web.resources.common;

import ch.globaz.tmmas.personnesservice.application.common.ErrorCode;
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

	public ErrorResponseResource(HttpStatus status, String message, List<String> erreurs) {
		this.erreurs = erreurs;
		this.message = message;
		this.status = status;
		this.timestamp = new Date();
	}

	public ErrorResponseResource(HttpStatus status, String message, String erreur, ErrorCode errorCode) {
		this(status,message, Arrays.asList(erreur));
		this.errorCode = errorCode;
	}

	public ErrorResponseResource(HttpStatus status, String message, String erreur) {
		this(status,message, Arrays.asList(erreur));
	}

	public ErrorResponseResource(HttpStatus status, String message) {
		this.message = message;
		this.status = status;
		this.timestamp = new Date();
	}

}
