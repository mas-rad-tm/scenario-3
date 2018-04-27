package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.domain.model.NSS;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.domain.repository.PersonneRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class PersonnesHibernateRepository extends HibernateRepository implements PersonneRepository {

	@Override
	public PersonneMorale creerPersonneMorale(PersonneMorale personneMorale) {
		getSession().saveOrUpdate(personneMorale);
		return personneMorale;
	}

	@Override
	public PersonneMorale synchoniser(PersonneMorale personneMorale) {
		personneMorale = (PersonneMorale) getSession().merge(personneMorale);
		return personneMorale;
	}

	@Override
	public Boolean personneExist(Long personneId){
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<PersonneMorale> criteria = builder.createQuery(PersonneMorale.class);
		Root<PersonneMorale> root = criteria.from(PersonneMorale.class);

		criteria.select(root.get("id"))
				.where(builder.equal(root.get("id"), personneId));
		Query<PersonneMorale> q = getSession().createQuery(criteria);
		return q.uniqueResult() != null;
	}

	@Override
	public Optional<PersonneMorale> getPersonneById(Long id) {
		PersonneMorale personneMorale =  getSession().get(PersonneMorale.class, id);
		return Optional.ofNullable(personneMorale);
	}

	@Override
	public PersonneMorale mettreAJour(PersonneMorale personneMorale){


		//this.getSession().flush();
		personneMorale = (PersonneMorale) this.getSession().merge(personneMorale);
		//this.getSession().saveOrUpdate(personneMorale);
		//this.getSession().update(personneMorale);
		return personneMorale;
	}

	@Override
	public Optional<PersonneMorale> getPersonneByNss(String nss) {

		CriteriaBuilder builder = getSession().getCriteriaBuilder();

		CriteriaQuery<PersonneMorale> criteria = builder.createQuery(PersonneMorale.class);
		Root<PersonneMorale> root = criteria.from(PersonneMorale.class);

		criteria.select(root).where(builder.equal(root.get("nss"), new NSS(nss)));

		Query<PersonneMorale> q = getSession().createQuery(criteria);

		List<PersonneMorale> personnes = q.getResultList();

		PersonneMorale personneMorale = null;

		if(personnes.size() > 0){
			personneMorale =q .getSingleResult();
		}


		return Optional.ofNullable(personneMorale);
	}

	@Override
	public List<PersonneMorale> listerPersonnes() {
		return getSession().createQuery("FROM " + PersonneMorale.class.getSimpleName()).list();
	}


}
