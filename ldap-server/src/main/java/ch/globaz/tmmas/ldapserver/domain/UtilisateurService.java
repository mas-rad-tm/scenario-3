package ch.globaz.tmmas.ldapserver.domain;

import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;

import java.util.List;

public interface UtilisateurService {
    List<String> getAllPersonNames();

    List<String> searchByUsername(String username);

    UtilisateursLdap getByUUID(String uid);

    UtilisateursLdap authenticate(String username, String password);

    List<String> getGroupes();
}
