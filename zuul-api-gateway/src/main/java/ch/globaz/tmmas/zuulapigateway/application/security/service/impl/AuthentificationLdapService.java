package ch.globaz.tmmas.zuulapigateway.application.security.service.impl;

import ch.globaz.tmmas.zuulapigateway.application.security.service.AuthentificationService;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.LdapAuthentificationClient;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.UtilisateurLdapDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthentificationLdapService implements AuthentificationService {

    private final LdapAuthentificationClient ldapAuthentificationClient;

    @Autowired
    public AuthentificationLdapService(LdapAuthentificationClient ldapAuthentificationClient){
        this.ldapAuthentificationClient = ldapAuthentificationClient;
    }

    @Override
    public Optional<UtilisateurLdapDto> authentifie(LoginDto dto) {

        Optional<UtilisateurLdapDto> udto = ldapAuthentificationClient.authentifie(dto);

        if(udto.isPresent()){
            return Optional.of(udto.get());
        }

        return Optional.empty();
    }
}
