package ch.globaz.tmmas.ldapserver.application.api.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

/**
 * Ressources utilisées pour toutes les exceptions générés au niveau des requêtes REST
 */
@Getter
public class ErrorResponseResource {

	private HttpStatus status;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private List<String> erreurs;

	public ErrorResponseResource(HttpStatus status, String message, List<String> erreurs) {
		this.erreurs = erreurs;
		this.message = message;
		this.status = status;
	}

	public ErrorResponseResource(HttpStatus status, String message, String erreur) {
		this(status,message, Arrays.asList(erreur));
	}

	public ErrorResponseResource(HttpStatus status, String message) {
		this.message = message;
		this.status = status;
	}

}
