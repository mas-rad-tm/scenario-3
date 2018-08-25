package ch.globaz.tmmas.personnesservice.infrastructure.authentifcation;

import ch.globaz.tmmas.personnesservice.infrastructure.security.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LdapAuthentificationClient {


    private final String ldapUri = "http://localhost:8010/ldap-server/auth";

    @Autowired
    private RestTemplate restTemplate;


    //public LdapAuthentificationClient(RestTemplate restTemplate){
     //   this.restTemplate = restTemplate;
   // }

    public UtilisateurLdapDto authentifie(LoginDto dto){

        UtilisateurLdapDto utilisateur;

        try{
            utilisateur = restTemplate.postForObject(ldapUri,dto,UtilisateurLdapDto.class);
        }catch (RemoteAccessException e){
            throw e;
        }

        return utilisateur;
    }
}
