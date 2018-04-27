package ch.globaz.tmmas.personnesservice.domain.service;

import ch.globaz.tmmas.personnesservice.domain.common.GlobalParams;
import ch.globaz.tmmas.personnesservice.domain.exception.AdresseIncoherenceException;
import ch.globaz.tmmas.personnesservice.domain.factory.AdresseFactory;
import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.AdressesRepository;
import ch.globaz.tmmas.personnesservice.reglesmetiers.DateDebutAdresseActivePersonneCoherente;

import java.time.Period;
import java.time.format.DateTimeFormatter;

public class AdressePersonneService {

	public static Adresse addAdresseToPersonneMorale(PersonneMorale personneMorale, Adresse nouvelleAdresse, AdressesRepository
			adresseRepository) throws AdresseIncoherenceException {

		if(personneMorale.hasAdressesActiveDefinies()){

			Adresse adresseActive = personneMorale.getAdresseActive();

			DateDebutAdresseActivePersonneCoherente coherenceDates = new DateDebutAdresseActivePersonneCoherente(
					adresseActive.getDateDebutValidite());

			if(coherenceDates.isSatisfiedBy(nouvelleAdresse.getDateDebutValidite())){
				throw new AdresseIncoherenceException(String.format("La date de but de la nouvelle adresse [%s] "
						+ "doit être apprès la date de l'adresse active actuelle %s",
						nouvelleAdresse.getDateDebutValidite().format(DateTimeFormatter.ofPattern(GlobalParams.DATE_FORMATTER_PATTER.value)),
						adresseActive.getDateDebutValidite().format(DateTimeFormatter.ofPattern(GlobalParams.DATE_FORMATTER_PATTER.value))));
			}

			//Désactivation ancienne adresse
			adresseActive.desactive(nouvelleAdresse.getDateDebutValidite());
			adresseRepository.mettreAJour(adresseActive);

			//Création nouvelle adresse
			adresseRepository.creerAdresse(nouvelleAdresse);
			personneMorale.setAdresseActive(nouvelleAdresse);


		}else{
			adresseRepository.creerAdresse(nouvelleAdresse);
			personneMorale.setAdresseActive(nouvelleAdresse);
		}
		return nouvelleAdresse;

	}
}
