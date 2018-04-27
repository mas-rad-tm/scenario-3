package ch.globaz.tmmas.personnesservice.application.event;

import ch.globaz.tmmas.personnesservice.domain.event.DomainEvent;

public interface InternalEventPublisher {
    void publishEvent(DomainEvent event);
}
