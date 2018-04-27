package ch.globaz.tmmas.personnesservice.domain.repository;

import ch.globaz.tmmas.personnesservice.domain.model.Localite;

import java.util.Optional;

public interface LocaliteRepository {

	Localite creerLocalite(Localite localite);

	Optional<Localite> findById(Long id);
}
