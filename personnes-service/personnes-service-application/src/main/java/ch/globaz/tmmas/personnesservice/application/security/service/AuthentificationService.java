package ch.globaz.tmmas.personnesservice.application.security.service;

import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.personnesservice.infrastructure.authentifcation.UtilisateurLdapDto;
import ch.globaz.tmmas.personnesservice.infrastructure.security.Utilisateur;

import java.util.Optional;

public interface AuthentificationService {
    Optional<UtilisateurLdapDto> authentifie(LoginDto dto);
}
