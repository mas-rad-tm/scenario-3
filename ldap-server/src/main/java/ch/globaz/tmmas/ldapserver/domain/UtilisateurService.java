package ch.globaz.tmmas.ldapserver.domain;

import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;

import java.util.List;

public interface UtilisateurService {


	List<UtilisateursLdap> getAllPersonnes();

	List<String> searchByUsername(String username);

    UtilisateursLdap getByUUID(String uid);

    UtilisateursLdap authenticate(String username, String password);


	List<String> getRoles();

	List<String> getRolesFor(String uid);
}
