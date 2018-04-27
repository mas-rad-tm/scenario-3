package ch.globaz.tmmas.personnesservice.domain.repository;

import ch.globaz.tmmas.personnesservice.domain.model.Adresse;

import java.util.List;
import java.util.Optional;

public interface AdressesRepository {

    Adresse creerAdresse(Adresse adresse);

	Adresse mettreAJour(Adresse adresse);

	Optional<Adresse> getAdresseActiveByPersonne(Long personneId);

	List<Adresse> getAdresseaByPersonne(Long personneId);
}
