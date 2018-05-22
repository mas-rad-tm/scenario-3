package lab.application.api.web.rest;

import lab.application.security.configuration.JwtSettings;
import lab.application.security.configuration.SecurityConfiguration;
import lab.application.security.exception.InvalidJwtToken;
import lab.application.security.jwt.*;
import lab.application.security.jwt.verifier.TokenVerifier;
import lab.application.service.UtilisateurService;
import lab.application.service.impl.UtilisateurDetailService;
import lab.model.ContexteUtilisateur;
import lab.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@RestController
public class RefreshTokenController {

	private final JwtTokenFactory tokenFactory;

	private final JwtSettings jwtSettings;

	private final UtilisateurService utilisateurService;

	private TokenVerifier tokenVerifier;

	private TokenExtractor tokenExtractor;

	@Autowired
	public RefreshTokenController(JwtTokenFactory tokenFactory, UtilisateurService utilisateurService, JwtSettings
			jwtSettings, TokenExtractor tokenExtractor, TokenVerifier tokenVerifier){
		this.tokenFactory = tokenFactory;
		this.jwtSettings = jwtSettings;
		this.utilisateurService = utilisateurService;
		this.tokenExtractor = tokenExtractor;
		this.tokenVerifier = tokenVerifier;


	}

	@RequestMapping(value="/api/auth/token", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String tokenPayload = tokenExtractor.extract(request.getHeader(SecurityConfiguration.AUTHENTICATION_HEADER_NAME));

		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
		RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

		String jti = refreshToken.getJti();
		if (!tokenVerifier.verify(jti)) {
			throw new InvalidJwtToken();
		}

		String subject = refreshToken.getSubject();

		Utilisateur utilisateur = utilisateurService.loadUserByUsername(subject).orElseThrow(() ->
				new UsernameNotFoundException("User not found: " + subject));

		if (utilisateur.getRoles() == null)
			throw new InsufficientAuthenticationException("User has no roles assigned");


		ContexteUtilisateur contexteUtilisateur = ContexteUtilisateur.create(utilisateur.getUsername(),
				new ArrayList<>(utilisateur.getAuthorities()));

		return tokenFactory.createAccessJwtToken(contexteUtilisateur);
	}

}
