package lab.application.security.provider;

import lab.application.service.impl.UtilisateurDetailService;
import lab.model.ContexteUtilisateur;
import lab.model.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Provider permettant de traiter l'authentification lors du login.
 * L'authenficiation du login est sous la forme nom/pass
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    private final PasswordEncoder passwordEncoder;
    private final UtilisateurDetailService utilisateurDetailService;

    @Autowired
    public LoginAuthenticationProvider(final UtilisateurDetailService utilisateurDetailService,
                                       final PasswordEncoder passwordEncoder) {
        this.utilisateurDetailService = utilisateurDetailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        Utilisateur utilisateur = utilisateurDetailService.loadUserByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Utilisateur inexistant: " + username));



        if (!passwordEncoder.matches(password, utilisateur.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (utilisateur.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");


        logger.info("Utilisateur before roles: {}",utilisateur);

        /**List<GrantedAuthority> authorities = utilisateur.getAuthorities().stream()
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.);
                })
                .collect(Collectors.toList());
*/
        ContexteUtilisateur contexteUtilisateur = ContexteUtilisateur.create(utilisateur.getUsername(), utilisateur.getAuthoritiesAsList());

        logger.info("Contexte Utilisateur : {}",contexteUtilisateur);

        return new UsernamePasswordAuthenticationToken(contexteUtilisateur, null, contexteUtilisateur.getAuthorities());
    }

    /**
     * Type d'authentification supporté par le provider
     * @param authentication le type d'authentification supporté
     * @return un booléen qui teste le type
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
