package ch.globaz.tmmas.personnesservice.domain.event;

import ch.globaz.tmmas.personnesservice.domain.common.GlobalParams;

import java.time.format.DateTimeFormatter;

public interface DomainEvent {
    DateTimeFormatter formatter
            = DateTimeFormatter.ofPattern(GlobalParams.DATE_FORMATTER_PATTER.value);
}
