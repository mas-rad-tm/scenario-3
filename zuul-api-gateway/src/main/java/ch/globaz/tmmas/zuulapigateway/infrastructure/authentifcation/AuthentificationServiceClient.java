package ch.globaz.tmmas.zuulapigateway.infrastructure.authentifcation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class AuthentificationServiceClient {

    @Value("${auth-service-url}")
    private String authServiceUrl;

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthentificationServiceClient.class);

    //private final String ldapUri = "http://localhost:8020/authentification-service/auth";

    @Autowired
    private RestTemplate restTemplate;


    public Optional<UtilisateurDto> authentifie(LoginDto dto){

        LOGGER.info("Call remote rest authentification service. Url:{}, dto: {}", authServiceUrl,dto);

        ResponseEntity<UtilisateurDto> utilisateurLdapDtoResponse = null;

        try{
            utilisateurLdapDtoResponse = restTemplate
                    .exchange(authServiceUrl, HttpMethod.POST, new HttpEntity<>(dto), UtilisateurDto.class);
        }catch (Exception ex){

            LOGGER.error("Problem with authentification with remote authentification service");
            return Optional.empty();
        }


        return Optional.of(utilisateurLdapDtoResponse.getBody());
    }
}
