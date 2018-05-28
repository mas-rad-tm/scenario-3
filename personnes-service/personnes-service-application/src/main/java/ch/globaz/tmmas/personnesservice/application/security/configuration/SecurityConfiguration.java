package ch.globaz.tmmas.personnesservice.application.security.configuration;

import ch.globaz.tmmas.personnesservice.application.security.filter.JwtAuthentificationProcessingFilter;
import ch.globaz.tmmas.personnesservice.application.security.filter.LoginProcessingFilter;
import ch.globaz.tmmas.personnesservice.application.security.handler.AuthentificationSuccessHandler;
import ch.globaz.tmmas.personnesservice.application.security.handler.AuthentificationFailureHandler;
import ch.globaz.tmmas.personnesservice.application.security.jwt.JwtSkipPathRequestMatcher;
import ch.globaz.tmmas.personnesservice.application.security.jwt.TokenExtractor;
import ch.globaz.tmmas.personnesservice.application.security.provider.JwtAuthenticationProvider;
import ch.globaz.tmmas.personnesservice.application.security.provider.LoginAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity  //activation de la sécurité de l'application
/**
 * The prePostEnabled property enables Spring Security pre/post annotations
 The securedEnabled property determines if the @Secured annotation should be enabled
 The jsr250Enabled property allows us to use the @RoleAllowed annotation
 */
@EnableGlobalMethodSecurity(  //activation de la sécurité des méthodes via aop
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private static final String AUTHENTICATION_URL = "/login";
    private static final String API_ROOT_URL = "/**";
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";


    @Autowired
    private  AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthentificationSuccessHandler successHandler;
    @Autowired
    private AuthentificationFailureHandler failureHandler;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private TokenExtractor tokenExtractor;


//    @Autowired
//    public SecurityConfiguration(AuthenticationEntryPoint authenticationEntryPoint,ObjectMapper objectMapper,
//                                 JwtAuthenticationSuccessHandler successHandler,JwtAuthentificationFailureHandler failureHandler,
//                                 AuthenticationManager authenticationManager, LoginAuthenticationProvider loginAuthenticationProvider){
//        this.authenticationEntryPoint = authenticationEntryPoint;
//        this.objectMapper = objectMapper;
//        this.failureHandler = failureHandler;
//        this.successHandler = successHandler;
//        this.authenticationManager = authenticationManager;
//        this.loginAuthenticationProvider = loginAuthenticationProvider;
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                AUTHENTICATION_URL,
                "/h2",
                "/",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/h2/**"
        );

        http
                .csrf().disable() // Pas besoin de csrf avec une auth jwt

                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                .permitAll()

                .and()
                .authorizeRequests()
                .antMatchers(API_ROOT_URL).authenticated() // Protection de tout

                .and()
                //.addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,API_ROOT_URL), UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();



    }

    /**
     * Fourni une instance du filtre de traitement des authentifications jwt
     * @param pathsToSkip la liste des endpoints non impactés par le filtre
     * @param pattern le pattern de l'uri de la ressource rest
     * @return une instance du filtre
     * @throws Exception
     */
    protected JwtAuthentificationProcessingFilter buildJwtTokenAuthenticationProcessingFilter(List<String> pathsToSkip, String pattern) throws Exception {
        JwtSkipPathRequestMatcher matcher = new JwtSkipPathRequestMatcher(pathsToSkip, pattern);
        JwtAuthentificationProcessingFilter filter
                = new JwtAuthentificationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    private Filter buildLoginProcessingFilter(String loginEntryPoint) {
        LoginProcessingFilter filter = new LoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
