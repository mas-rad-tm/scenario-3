package ch.globaz.tmmas.personnesservice.application.service;

import ch.globaz.tmmas.personnesservice.domain.command.CreerAdresseCommand;
import ch.globaz.tmmas.personnesservice.domain.exception.AdresseIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AdressesService {

    //Adresse createAdresse(CreerAdresseCommand adresse, Long personneId) throws AdresseIncoherenceException;



    Adresse createAdresse(CreerAdresseCommand command, Long personneId) throws AdresseIncoherenceException;

	@Transactional
	List<Adresse> listerAdresseForPersonne(Long personneId) throws
	        AdresseIncoherenceException;
}
