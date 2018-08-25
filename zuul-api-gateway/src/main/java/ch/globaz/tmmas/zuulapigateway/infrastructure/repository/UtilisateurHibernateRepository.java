package ch.globaz.tmmas.zuulapigateway.infrastructure.repository;

import ch.globaz.tmmas.zuulapigateway.infrastructure.security.Utilisateur;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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
