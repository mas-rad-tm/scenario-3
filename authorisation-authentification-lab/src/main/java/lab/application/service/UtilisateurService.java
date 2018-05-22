package lab.application.service;

import lab.model.Utilisateur;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UtilisateurService {

    Optional<Utilisateur> loadUserByUId(Long userId);

    Optional<Utilisateur> loadUserByUsername(String userName);
}
