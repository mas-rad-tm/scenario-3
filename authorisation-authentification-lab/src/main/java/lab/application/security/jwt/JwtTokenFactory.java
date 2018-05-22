package lab.application.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lab.application.security.configuration.JwtSettings;
import lab.model.Scopes;
import lab.model.ContexteUtilisateur;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 * 
 * @author vladimir.stankovic
 *
 * May 31, 2016
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings settings;
    private Logger logger = LoggerFactory.getLogger(JwtTokenFactory.class.getName());

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }

    /**
     * Méthode de création de nouveau token d'accès
     * @param contexteUtilisateur le contexte utilisateur sur la base duquel créer le token
     * @return une instance de {@link AccessJwtToken} repérsentant le token jwt
     */
    public AccessJwtToken createAccessJwtToken(ContexteUtilisateur contexteUtilisateur) {

        logger.info("Create access token utilisateur: {}",contexteUtilisateur);

        if (StringUtils.isBlank(contexteUtilisateur.getUsername()))
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (contexteUtilisateur.getAuthorities() == null || contexteUtilisateur.getAuthorities().isEmpty())
            throw new IllegalArgumentException("User doesn't have any privileges");

        Claims claims = Jwts.claims().setSubject(contexteUtilisateur.getUsername());
        claims.put("scopes", contexteUtilisateur.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        LocalDateTime currentTime = LocalDateTime.now();
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .setExpiration(Date.from(currentTime
              .plusMinutes(settings.getTokenExpirationTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(ContexteUtilisateur contexteUtilisateur) {
        if (StringUtils.isBlank(contexteUtilisateur.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(contexteUtilisateur.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .setExpiration(Date.from(currentTime
              .plusMinutes(settings.getRefreshTokenExpTime())
              .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }
}
