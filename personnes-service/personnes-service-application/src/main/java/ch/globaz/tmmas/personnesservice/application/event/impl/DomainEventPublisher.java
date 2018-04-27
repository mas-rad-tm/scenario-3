package ch.globaz.tmmas.personnesservice.application.event.impl;

import ch.globaz.tmmas.personnesservice.application.event.InternalEventPublisher;
import ch.globaz.tmmas.personnesservice.domain.event.DomainEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher implements InternalEventPublisher {

	@Autowired
	ApplicationEventPublisher commandPublisher;

	@Override
	public void publishEvent(DomainEvent event) {

		commandPublisher.publishEvent(event);
	}
}
