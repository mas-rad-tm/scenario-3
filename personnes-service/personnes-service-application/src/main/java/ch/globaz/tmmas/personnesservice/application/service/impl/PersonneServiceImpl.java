package ch.globaz.tmmas.personnesservice.application.service.impl;

import ch.globaz.tmmas.personnesservice.application.api.web.controller.PersonnesController;
import ch.globaz.tmmas.personnesservice.application.event.InternalEventPublisher;
import ch.globaz.tmmas.personnesservice.application.service.PersonneService;
import ch.globaz.tmmas.personnesservice.domain.command.CreerPersonneMoraleCommand;
import ch.globaz.tmmas.personnesservice.domain.event.PersonneMoraleCreeEvent;
import ch.globaz.tmmas.personnesservice.domain.exception.PersonnesIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.factory.PersonneMoraleFactory;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.PersonneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PersonneServiceImpl implements PersonneService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PersonneServiceImpl.class);

	@Autowired
	PersonneRepository personneRepository;

	@Autowired
	InternalEventPublisher eventPublisher;

	@Transactional
	@Override
	public PersonneMorale creerPersonneMorale(CreerPersonneMoraleCommand command) throws PersonnesIncoherenceException {

		PersonneMorale personneMorale = personneRepository.creerPersonneMorale(
				PersonneMoraleFactory.creerPersonne(command,personneRepository)
		);
		eventPublisher.publishEvent(PersonneMoraleCreeEvent.fromEntity(personneMorale));
		return personneMorale;

	}

	@Transactional
	@Override
	public Boolean checkifPersonneExist(Long personneId){

		LOGGER.info("Check if personne exist, id {}", personneId);

		return personneRepository.personneExist(personneId);
	}

	@Transactional
	@Override
	public Optional<PersonneMorale> getPersonneById(Long id) {

		return personneRepository.getPersonneById(id).map(personne -> {

			return Optional.of(personne);
		}).orElseGet(Optional::empty);

	}

	@Transactional
	@Override
	public PersonneMorale mettreAJour(PersonneMorale personneMorale) {
		return personneRepository.mettreAJour(personneMorale);
	}

	@Transactional
	@Override
	public List<PersonneMorale> getAllPersonnes() {
		return personneRepository.listerPersonnes();
	}



}
