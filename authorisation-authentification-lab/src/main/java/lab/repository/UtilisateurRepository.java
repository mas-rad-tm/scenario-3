package lab.repository;

import lab.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Utilisateur findByEmail(String email);

    Utilisateur findByNomUtilisateur(String login);

    Optional<Utilisateur> findById(Long id);

    //boolean existsByLogin(String nomUtilisateur);
}
