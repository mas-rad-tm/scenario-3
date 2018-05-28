package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.domain.model.NSS;
import ch.globaz.tmmas.personnesservice.domain.model.PersonneMorale;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Role;
import ch.globaz.tmmas.personnesservice.infrastructure.security.TypeRole;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleHibernateRepository extends HibernateRepository{

    public Optional<Role> findByRoleType(TypeRole typeRole) {

        CriteriaBuilder builder = getSession().getCriteriaBuilder();

        CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
        Root<Role> root = criteria.from(Role.class);

        criteria.select(root).where(builder.equal(root.get("typeRole"), typeRole));

        Query<Role> q = getSession().createQuery(criteria);

        List<Role> roles = q.getResultList();

        Role role = null;

        if(roles.size() > 0){
            role = q .getSingleResult();
        }


        return Optional.ofNullable(role);
    }

    public Role save(Role role) {

        getSession().save(role);

        return role;
    }
}
