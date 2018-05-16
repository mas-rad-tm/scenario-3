package ch.globaz.tmmas.authentificationservice.application.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component
public class CustomLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    //@Autowired
    //private UserService userService;

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(DirContextOperations userData, String username) {
        //UserEntity userEntity = userService.findByUsername(nomUtilisateur);

        //if (userEntity == null) {
        //    throw new UsernameNotFoundException(String.format("No user found with nomUtilisateur '%s'.", nomUtilisateur));
        //}
        Collection<? extends GrantedAuthority> authorities = userAuthorityService.getGrantedAuthorities(true);

        return authorities;
    }
}