package ch.globaz.tmmas.authentificationservice.application.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorMessage {
    public static final String INVALID_USERNAME_OR_PASSWORD = "Invalid username or password";

    public ErrorMessage(String message, String code) {
		this(message, code, "");
	}

	public ErrorMessage(String message) {
		this(message, "", "");
	}

	private String message;

	private String code;

	private String detail;

}
