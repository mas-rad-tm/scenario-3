package ch.globaz.tmmas.authentificationservice.infrastructure.authentifcation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class LdapAuthentificationServiceClient {

    @Value("${ldap-server-url}")
    private String ldapserverUrl;

    private final static Logger LOGGER = LoggerFactory.getLogger(LdapAuthentificationServiceClient.class);

    //private final String ldapUri = "http://localhost:8020/authentification-service/auth";

    @Autowired
    private RestTemplate restTemplate;


    public Optional<LdapUtilisateurDto> authentifie(LoginDto dto){

        LOGGER.info("Call remote rest authentification service. Url:{}, dto: {}", ldapserverUrl,dto);

        ResponseEntity<LdapUtilisateurDto> utilisateurLdapDtoResponse = null;

        try{
            utilisateurLdapDtoResponse = restTemplate
                    .exchange(ldapserverUrl, HttpMethod.POST, new HttpEntity<>(dto), LdapUtilisateurDto.class);
        }catch (Exception ex){

            LOGGER.error("Problem with authentification with remote ldap server");
            return Optional.empty();
        }


        return Optional.of(utilisateurLdapDtoResponse.getBody());
    }
}
