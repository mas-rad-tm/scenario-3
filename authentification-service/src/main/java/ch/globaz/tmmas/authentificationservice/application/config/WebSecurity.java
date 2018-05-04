package ch.globaz.tmmas.authentificationservice.application.config;

import ch.globaz.tmmas.authentificationservice.application.filter.JwtAuthentificationFilter;
import ch.globaz.tmmas.authentificationservice.application.filter.JwtAuthorisationFilter;
import ch.globaz.tmmas.authentificationservice.domain.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static ch.globaz.tmmas.authentificationservice.application.filter.SecurityConstants.LOGIN_URL;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	private ContextSource contextSource;

	@Autowired
	private LdapTemplate ldapTemplate;
	@Autowired
	private Environment env;







	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(BCryptPasswordEncoder bCryptPasswordEncoder) {
		//this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().ignoringAntMatchers("/login");

		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers("/login")
				.permitAll()
				.anyRequest().authenticated()
		.and()
				.addFilter(new JwtAuthentificationFilter(authenticationManager()))
			.addFilter(new JwtAuthorisationFilter(authenticationManager()));
				//.antMatchers("/**/*")
				//.denyAll();
/**
		System.out.println("**************************");
		System.out.println(authenticationManager());


		http.cors().and().csrf().disable().authorizeRequests()
				//.antMatchers(HttpMethod.POST, LOGIN_URL)
				.antMatchers("**")
				.permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter(new JwtAuthentificationFilter(authenticationManager()))
				.addFilter(new JwtAuthorisationFilter(authenticationManager()))
				// this disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

 */
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		//contextSource.getContext("uid=" + "sce" + ",ou=utilisateurs," + env.getRequiredProperty("spring.ldap" +
		//		".embedded.partitionSuffix"), "secret");



		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}

	/**
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	*/
}
