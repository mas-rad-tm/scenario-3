package ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class LdapAuthentificationClient {

    private final static Logger LOGGER = LoggerFactory.getLogger(LdapAuthentificationClient.class);

    private final String ldapUri = "http://localhost:8010/ldap-server/auth";

    @Autowired
    private RestTemplate restTemplate;


    public Optional<UtilisateurLdapDto> authentifie(LoginDto dto){

        LOGGER.info("Call remote rest authentification service. Url:{}, dto: {}", ldapUri,dto);

        ResponseEntity<UtilisateurLdapDto> utilisateurLdapDtoResponse = null;

        try{
            utilisateurLdapDtoResponse = restTemplate
                    .exchange(ldapUri, HttpMethod.POST, new HttpEntity<>(dto), UtilisateurLdapDto.class);
        }catch (Exception ex){

            LOGGER.error("Problem with authentification with remote ldap");
            return Optional.empty();
        }


        return Optional.of(utilisateurLdapDtoResponse.getBody());
    }
}
