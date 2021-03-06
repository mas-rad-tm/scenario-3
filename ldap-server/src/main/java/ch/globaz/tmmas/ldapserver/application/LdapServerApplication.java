package ch.globaz.tmmas.ldapserver.application;

import ch.globaz.tmmas.ldapserver.domain.UtilisateurService;
import ch.globaz.tmmas.ldapserver.domain.model.UtilisateursLdap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@SpringBootApplication
@ComponentScan(basePackages = {"ch.globaz.tmmas.ldapserver"})
public class LdapServerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapServerApplication.class);

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    ContextSource contextSource;

    @Autowired
    UtilisateurService utilisateurService;

    /**
     * Méthode exécutable démarrant l'application en mode jar
     * @param args les arguments d'entrées
     */
    public static void main(String []args)  {
        SpringApplication.run(LdapServerApplication.class);
    }


    @PostConstruct
    public void setup(){
        logInitApplicationContext();
    }



    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private  void logInitApplicationContext () {
        Environment env = appContext.getEnvironment();

        ResourceProperties prop = new ResourceProperties();
        String[] staticLocation = prop.getStaticLocations();
        LOGGER.info("***********************************************************************");
        LOGGER.info("*                     *** Static location paths ***                   *");
        LOGGER.info("***********************************************************************");

        Arrays.asList(staticLocation).forEach(location -> LOGGER.info("* {}",location));
        LOGGER.info("***********************************************************************");


        String externalAdress;

        try {
            externalAdress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            externalAdress = "Undefined";
        }
        LOGGER.info("***********************************************************************");
        LOGGER.info("*            *** Application context configuration ***                *");
        LOGGER.info("***********************************************************************");

        LOGGER.info("* Application '{}' is running!",env.getProperty("spring.application.name"));
        LOGGER.info("* Local      : localhost:{}{}",
                env.getProperty("server.port"), env.getProperty("server.servlet.context-path"));
        LOGGER.info("* External   : {}:{}{}",externalAdress, env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path"));

        String activeProfiles = Arrays.asList(env.getActiveProfiles()).stream()
                .collect( Collectors.joining( "," ) );
        LOGGER.info("* Profile(s) : {} ",activeProfiles);

        logProxyInfo();

        LOGGER.info("***********************************************************************");

    }

    private void logProxyInfo() {

        Optional<String> httpProxy = Optional.ofNullable(System.getProperties().getProperty("http.proxyHost"));
        Optional<String> httpsProxy = Optional.ofNullable(System.getProperties().getProperty("https.proxyHost"));

        if(httpProxy.isPresent()){
            LOGGER.info("* HTTP Proxy : {}:{} ",httpProxy,
                    System.getProperties().getProperty("http.proxyPort"));
            LOGGER.info("* HTTPS Proxy: {}:{} ",httpsProxy,
                    System.getProperties().getProperty("https.proxyPort"));
        }

    }
}
