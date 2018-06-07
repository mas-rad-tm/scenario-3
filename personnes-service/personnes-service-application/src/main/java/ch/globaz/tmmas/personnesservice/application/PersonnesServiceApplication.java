package ch.globaz.tmmas.personnesservice.application;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Classe principale de l'application en mode jar.
 * Le serveur est embarqué par l'application
 * <b>Enable AutoConfiguration Exclusion:</b>HibernateJpaAutoConfiguration.class
 *
 */

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@ComponentScan(basePackages = {"ch.globaz.tmmas.personnesservice","ch.globaz.tmmas.personnesservice.application.security.audit"})
public class PersonnesServiceApplication {


	private final Environment env;
	private static final Logger LOGGER = LoggerFactory.getLogger(PersonnesServiceApplication.class);

	public PersonnesServiceApplication(Environment env) {
		this.env = env;
	}

	/**
	 * Méthode exécutable démarrant l'application en mode jar
	 * @param args les arguments d'entrées
	 */
	public static void main(String []args)  {

		SpringApplication app = new SpringApplication(PersonnesServiceApplication.class);

		Environment env = app.run(args).getEnvironment();

		logInitApplicationContext(env);

	}




	private static void logInitApplicationContext (Environment env) {

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

	private static void logProxyInfo() {

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
