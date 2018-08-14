package ch.globaz.tmmas.zuulapigateway.application.security.provider;

import ch.globaz.tmmas.zuulapigateway.application.security.model.ContexteUtilisateur;
import ch.globaz.tmmas.zuulapigateway.application.security.service.AuthentificationService;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.UtilisateurDto;
import ch.globaz.tmmas.zuulapigateway.infrastructure.repository.UtilisateurHibernateRepository;
import ch.globaz.tmmas.zuulapigateway.infrastructure.security.Utilisateur;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Provider permettant de traiter l'authentification lors du login.
 * L'authenficiation du login est sous la forme nom/pass
 */
@Component
public class LoginAuthenticationProvider implements AuthenticationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAuthenticationProvider.class);

    private final PasswordEncoder passwordEncoder;
    private final AuthentificationService authentificationService;
    private final UtilisateurHibernateRepository utilisateurHibernateRepository;

    @Autowired
    public LoginAuthenticationProvider(final AuthentificationService authentificationService,
                                       final PasswordEncoder passwordEncoder,
                                       final UtilisateurHibernateRepository utilisateurHibernateRepository) {
        this.authentificationService = authentificationService;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurHibernateRepository = utilisateurHibernateRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        Assert.notNull(authentication, "No authentication data provided");

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //Authentification via la provider
        UtilisateurDto utilisateurLdap = authentificationService.authentifie(new LoginDto(username,password)).orElseThrow(
                () -> new UsernameNotFoundException("Problème lors de l'authentification de l'utilisateur "
                        + username));


        if (utilisateurLdap.getRoles() == null)
            throw new InsufficientAuthenticationException("User has no roles assigned");

        LOGGER.info("Utilisateur before roles from ad: {}",utilisateurLdap);

        Utilisateur utilisateur = utilisateurHibernateRepository.getByUsername(utilisateurLdap.getUid()).get();

        /**List<GrantedAuthority> authorities = utilisateur.getAuthorities().stream()
                .map(authority -> {
                    return new SimpleGrantedAuthority(authority.);
                })
                .collect(Collectors.toList());
*/


        ContexteUtilisateur contexteUtilisateur = ContexteUtilisateur.create(utilisateur.getUsername(), utilisateur.getAuthoritiesAsList());

        LOGGER.info("Contexte Utilisateur : {}",contexteUtilisateur);

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
