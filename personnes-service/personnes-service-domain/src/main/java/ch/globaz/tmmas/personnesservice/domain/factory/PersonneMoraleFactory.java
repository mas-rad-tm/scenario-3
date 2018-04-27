package ch.globaz.tmmas.personnesservice.domain.factory;

import ch.globaz.tmmas.personnesservice.domain.command.CreerPersonneMoraleCommand;
import ch.globaz.tmmas.personnesservice.domain.exception.PersonnesIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.model.NSS;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.PersonneRepository;
import ch.globaz.tmmas.personnesservice.reglesmetiers.NssNonExistant;

public class PersonneMoraleFactory {

	public static PersonneMorale creerPersonne(CreerPersonneMoraleCommand commande, PersonneRepository personneRepository) throws PersonnesIncoherenceException {

		NssNonExistant regle = new NssNonExistant(personneRepository);

		if(regle.isSatisfiedBy(commande.getNss())){
			return new PersonneMorale(new NSS(commande.getNss()),commande.getNom(),
					commande.getPrenom(),commande.getDateNaissance(),commande.getSexe());
		}

		throw new PersonnesIncoherenceException(String.format("Le nss [%s] est déjà utilisé",commande.getNss()));
	}
}
