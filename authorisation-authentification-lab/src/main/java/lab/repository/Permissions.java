package lab.repository;

import lab.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Permissions extends JpaRepository<Permission,Long> {

    Permission findByName(String name);
}
