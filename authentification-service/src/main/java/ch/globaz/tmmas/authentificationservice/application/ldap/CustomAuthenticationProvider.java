package ch.globaz.tmmas.authentificationservice.application.ldap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private LdapAuthenticationProvider ldapAuthProvider;



    //@Autowired
    //private UserService userService;

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

       /* String nomUtilisateur = authentication.getName();
        UserEntity userEntity = userService.findByUsername(nomUtilisateur);
        if (userEntity == null) {
            throw new UsernameNotFoundException(String.format("No user found with nomUtilisateur '%s'.", nomUtilisateur));
        }
        AuthTypeEnum authType = userEntity.getAuthType();
        switch (authType) {
            case SIMPLE:
                return daoAuthProvider.authenticate(authentication);
            case LDAP:
           */     return ldapAuthProvider.authenticate(authentication);
           /* default:
                throw new IllegalArgumentException(authType + " is not yet implemented!");
        }*/
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
