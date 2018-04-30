package ch.globaz.tmmas.authentificationservice.domain.repository;

import java.util.List;

public interface UtilisateurRepository {

    List<String> getAllPersonNames();

    List<String> searchByUsername(String username);

    void authenticate(String username, String password);

    List<String> getGroupes();
}
