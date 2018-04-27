package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.domain.model.Adresse;
import ch.globaz.tmmas.personnesservice.domain.repository.AdressesRepository;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class AdressesHibernateRepository extends HibernateRepository implements AdressesRepository {

    @Override
    public Adresse creerAdresse(Adresse adresse) {
        getSession().saveOrUpdate(adresse);
        return adresse;
    }


    @Override
    public Adresse mettreAJour(Adresse adresse){
        return (Adresse) getSession().merge(adresse);
    }


    @Override
    public Optional<Adresse> getAdresseActiveByPersonne(Long personneId) {

        CriteriaBuilder builder = getSession().getCriteriaBuilder();

        CriteriaQuery<Adresse> criteria = builder.createQuery(Adresse.class);
        Root<Adresse> root = criteria.from(Adresse.class);

        criteria.select(root).where(builder.equal(root.get("personneMorale"), personneId))
                .where(builder.equal(root.get("isActive"),Boolean.TRUE));

        Query<Adresse> q = getSession().createQuery(criteria);

        List<Adresse> adresses = q.getResultList();

        Adresse adresseActive = null;

        if(adresses.size() > 0){
            adresseActive =q .getSingleResult();
        }


        return Optional.ofNullable(adresseActive);
    }

    @Override
    public List<Adresse> getAdresseaByPersonne(Long personneId) {

        CriteriaBuilder builder = getSession().getCriteriaBuilder();

        CriteriaQuery<Adresse> criteria = builder.createQuery(Adresse.class);
        Root<Adresse> root = criteria.from(Adresse.class);

        criteria.select(root).where(builder.equal(root.get("personneMorale"), personneId));

        Query<Adresse> q = getSession().createQuery(criteria);

        List<Adresse> adresses = q.getResultList();


        return adresses;
    }
}
