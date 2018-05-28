package ch.globaz.tmmas.personnesservice.infrastructure.repository;

import ch.globaz.tmmas.personnesservice.infrastructure.security.Permission;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Role;
import ch.globaz.tmmas.personnesservice.infrastructure.security.TypePermission;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class PermissionsHibernateRepository extends HibernateRepository {




        public Optional<Permission> findByTypePermission(TypePermission type) {

            CriteriaBuilder builder = getSession().getCriteriaBuilder();

            CriteriaQuery<Permission> criteria = builder.createQuery(Permission.class);
            Root<Permission> root = criteria.from(Permission.class);

            criteria.select(root).where(builder.equal(root.get("typePermission"), type));

            Query<Permission> q = getSession().createQuery(criteria);

            List<Permission> permissions = q.getResultList();

            Permission permission = null;

            if(permissions.size() > 0){
                permission = q .getSingleResult();
            }


            return Optional.ofNullable(permission);
        }

    public Permission save(Permission permission) {

            getSession().save(permission);

            return permission;
    }
}
