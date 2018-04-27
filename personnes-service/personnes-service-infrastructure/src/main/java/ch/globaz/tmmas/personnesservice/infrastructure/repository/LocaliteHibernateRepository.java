package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.domain.model.Localite;
import ch.globaz.tmmas.personnesservice.domain.repository.LocaliteRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LocaliteHibernateRepository extends HibernateRepository implements LocaliteRepository  {


	@Override
	public Localite creerLocalite(Localite localite) {
		getSession().saveOrUpdate(localite);

		return localite;
	}

	@Override
	public Optional<Localite> findById(Long id) {
		Localite localite =  getSession().get(Localite.class, id);
		return Optional.ofNullable(localite);
	}
}
