package ch.globaz.tmmas.personnesservice.application.event;


import ch.globaz.tmmas.personnesservice.domain.command.DomainCommand;

public interface InternalCommandPublisher {

	void publishCommand(DomainCommand command);
}
