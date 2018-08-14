package ch.globaz.tmmas.authentificationservice.application.web;

import ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation.LdapAuthentificationServiceClient;
import ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation.LdapUtilisateurDto;
import ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("/auth")
@ComponentScan(basePackages = "ch.globaz.tmmas.authentificationservice")
public class AuthentificationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationController.class);

	@Autowired
	LdapAuthentificationServiceClient ldapService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<LdapUtilisateurDto> authenticateAndGetUser(@RequestBody AuthentificationRequest authrequest){

		LOGGER.info("AuthentificationController#authenticateAndGetUser, authRequest : {}", authrequest);


		LdapUtilisateurDto utilisateursLdap = ldapService.authentifie(new LoginDto(authrequest.getNomUtilisateur(),authrequest
				.getMotDePasse())).get();

		LOGGER.info("AuthentificationController#authenticateAndGetUser, utilisateurLdap: {}", utilisateursLdap);

		return ResponseEntity.ok(utilisateursLdap);
	}
}
