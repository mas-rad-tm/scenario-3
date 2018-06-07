package ch.globaz.tmmas.personnesservice.application.security.service.impl;

import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.UtilisateurLdapDto;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Role;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Utilisateur;
import ch.globaz.tmmas.personnesservice.application.security.service.AuthentificationService;
import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.LdapAuthentificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

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
