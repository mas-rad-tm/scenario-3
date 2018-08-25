package ch.globaz.tmmas.ldapserver.application.api.web;

import ch.globaz.tmmas.ldapserver.application.LdapServerApplication;
import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;
import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController("/auth")
public class AuthentificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationController.class);

	@Autowired
	UtilisateurService utilisateurService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UtilisateursLdap> authenticateAndGetUser(@RequestBody AuthentificationRequest authrequest){

		LOGGER.info("AuthentificationController#authenticateAndGetUser, authRequest : {}", authrequest);


		UtilisateursLdap utilisateursLdap = utilisateurService.authenticate(authrequest.getNomUtilisateur(),authrequest
				.getMotDePasse());

		LOGGER.info("AuthentificationController#authenticateAndGetUser, utilisateurLdap: {}", utilisateursLdap);

		return ResponseEntity.ok(utilisateursLdap);
	}
}
