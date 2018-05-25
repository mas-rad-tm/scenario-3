package ch.globaz.tmmas.ldapserver.application.api.web;

import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;
import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;

@RestController("/auth")
public class AuthentificationController {

	@Autowired
	UtilisateurService utilisateurService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<UtilisateursLdap> autenticateAndGetUser(@RequestBody AuthentificationRequest authrequest){

		return ResponseEntity.ok(utilisateurService.authenticate(authrequest.getNomUtilisateur(),authrequest
				.getMotDePasse()));
	}
}
