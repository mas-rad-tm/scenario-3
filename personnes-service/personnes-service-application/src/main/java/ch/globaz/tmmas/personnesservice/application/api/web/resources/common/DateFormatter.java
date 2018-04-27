package ch.globaz.tmmas.personnesservice.application.api.web.resources.common;

import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateFormatter {
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE, dd" +
			" MMMM, yyyy", Locale.FRENCH);
}
