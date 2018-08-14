package ch.globaz.tmmas.zuulapigateway.application.security.service.impl;

import ch.globaz.tmmas.zuulapigateway.application.security.service.AuthentificationService;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.AuthentificationServiceClient;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.LoginDto;
import ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation.UtilisateurDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthentificationServiceImpl implements AuthentificationService {

    private final AuthentificationServiceClient authentificationServiceClient;

    @Autowired
    public AuthentificationServiceImpl(AuthentificationServiceClient authentificationServiceClient){
        this.authentificationServiceClient = authentificationServiceClient;
    }

    @Override
    public Optional<UtilisateurDto> authentifie(LoginDto dto) {

        Optional<UtilisateurDto> udto = authentificationServiceClient.authentifie(dto);

        if(udto.isPresent()){
            return Optional.of(udto.get());
        }

        return Optional.empty();
    }
}
