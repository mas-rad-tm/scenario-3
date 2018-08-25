package ch.globaz.tmmas.zuulapigateway.application.security.jwt;


import ch.globaz.tmmas.zuulapigateway.application.security.model.ContexteUtilisateur;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * Une implémentation de  {@link org.springframework.security.core.Authentication}
 * Permettant une représentation simple d'un token jwt.
 *
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private RawAccessJwtToken rawAccessToken;
    private ContexteUtilisateur contexteUtilisateur;

    public JwtAuthenticationToken(RawAccessJwtToken unsafeToken) {
        super(null);
        this.rawAccessToken = unsafeToken;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(ContexteUtilisateur contexteUtilisateur) {
        super(contexteUtilisateur.getAuthorities());
        this.eraseCredentials();
        this.contexteUtilisateur = contexteUtilisateur;
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return rawAccessToken;
    }

    @Override
    public Object getPrincipal() {
        return this.contexteUtilisateur;
    }

    @Override
    public void eraseCredentials() {        
        super.eraseCredentials();
        this.rawAccessToken = null;
    }
}
