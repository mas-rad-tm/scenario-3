package ch.globaz.tmmas.zuulapigateway.application.security.service;

import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.UtilisateurLdapDto;

import java.util.Optional;

public interface AuthentificationService {
    Optional<UtilisateurLdapDto> authentifie(LoginDto dto);
}
