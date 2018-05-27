package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.domain.model.NSS;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Role;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Utilisateur;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class UtilisateurHibernateRepository extends HibernateRepository{

    public Utilisateur save(Utilisateur utilisateur) {

        getSession().save(utilisateur);

        return utilisateur;
    }

    @Transactional
    public Optional<Utilisateur> getByUsername(String username){

        Query query = getSession().createQuery("from Utilisateur where nomUtilisateur = :username ");
        query.setParameter("username", username);
        Utilisateur utilisateur = (Utilisateur) query.list().get(0);

        return Optional.ofNullable(utilisateur);
    }
}
