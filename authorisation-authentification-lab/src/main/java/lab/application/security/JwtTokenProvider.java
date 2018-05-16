package lab.application.security;


import io.jsonwebtoken.*;
import lab.model.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    //@Value("${app.jwtSecret}")
    private String jwtSecret = "secret";

    //@Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs = 100000;

    public String generateToken(Authentication authentication) {

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(Long.toString(utilisateur.getId()))
                .claim("user-detail",JwtUserDetailClaim.generateFromUtilisateur(utilisateur))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public Utilisateur getUserdFromJWT(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        JwtUserDetailClaim userDetails = (JwtUserDetailClaim) claims.get("user-details");

        return new Utilisateur(userDetails.getId(),userDetails.getNomUtilisateur(),userDetails.getRoles());

        //return (Utilisateur) claims.get("user-details");
    }

    public boolean validateToken(String authToken) {

        logger.info("{}#validateToken, token: {}",this.getClass().getName(),authToken);

        try {
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken).getBody();
            logger.info("Token valid to {}, {} min left",claims.getExpiration(),(claims.getExpiration().getTime() - new Date().getTime())/1000/60);
            return true;
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
