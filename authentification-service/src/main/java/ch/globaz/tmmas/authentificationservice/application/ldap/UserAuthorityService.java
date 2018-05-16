package ch.globaz.tmmas.authentificationservice.application.ldap;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class UserAuthorityService {

    public Collection<? extends GrantedAuthority> getGrantedAuthorities(boolean isAdmin) {

        Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}