package ch.globaz.tmmas.personnesservice.domain.repository;

import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;

import java.util.List;
import java.util.Optional;

public interface PersonneRepository {

	PersonneMorale creerPersonneMorale(PersonneMorale personneMorale);

    PersonneMorale synchoniser(PersonneMorale personneMorale);

	Boolean personneExist(Long personneId);

	Optional<PersonneMorale> getPersonneById(Long id);

    PersonneMorale mettreAJour(PersonneMorale personneMorale);

    Optional<PersonneMorale> getPersonneByNss(String nss);

    List<PersonneMorale> listerPersonnes();
}
