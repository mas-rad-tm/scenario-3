package ch.globaz.tmmas.authentificationservice.domain.repository;

import ch.globaz.tmmas.authentificationservice.domain.model.UtilisateursLdap;

import java.util.List;

public interface UtilisateurRepository {

    List<String> getAllPersonNames();

    List<String> searchByUsername(String username);

    UtilisateursLdap getByUUID(String uuid);

    UtilisateursLdap authenticate(String username, String password);

    List<String> getGroupes();
}
