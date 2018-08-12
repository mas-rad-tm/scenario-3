package ch.globaz.tmmas.zuulapigateway.application.security.model;

import lombok.ToString;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

/**
 * Classe représentant le contexte utilisateur. Càd l'utilisateur authentifié avec son nom et ses autorisations.
 * Utilisé pour la gestion du token wt
 */
@ToString
public class ContexteUtilisateur {

    private final String username;
    private final Collection<GrantedAuthority> authorities;

    private ContexteUtilisateur(String username, Collection<GrantedAuthority> authorities) {
        this.username = username;
        this.authorities = authorities;
    }
    
    public static ContexteUtilisateur create(String username, List<GrantedAuthority> authorities) {
        if (StringUtils.isBlank(username)) throw new IllegalArgumentException("Nom d'utilisateur est vide: " + username);
        return new ContexteUtilisateur(username, authorities);
    }


    public String getUsername() {
        return username;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
