package lab.application.security.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lab.application.security.JwtAuthenticationToken;
import lab.application.security.configuration.JwtSettings;
import lab.application.security.jwt.RawAccessJwtToken;
import lab.model.ContexteUtilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provider permettant de gérer l'authentification via le token jwt
 */
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{

    private final JwtSettings jwtSettings;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationProvider.class);


    @Autowired
    public JwtAuthenticationProvider(JwtSettings jwtSettings) {
        this.jwtSettings = jwtSettings;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


         RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
        String subject = jwsClaims.getBody().getSubject();

        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        ContexteUtilisateur context = ContexteUtilisateur.create(subject, authorities);

        logger.info("Authentcation jwt provider, subject {}, authorities: {}, userContext: {}, scopes [jwt]:{}",
                subject,authorities,context,scopes);

        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
