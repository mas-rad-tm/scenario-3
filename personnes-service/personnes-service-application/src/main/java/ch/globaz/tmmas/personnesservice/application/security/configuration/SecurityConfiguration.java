package ch.globaz.tmmas.personnesservice.application.security.configuration;

import ch.globaz.tmmas.personnesservice.application.security.filter.LoginProcessingFilter;
import ch.globaz.tmmas.personnesservice.application.security.handler.JwtAuthenticationSuccessHandler;
import ch.globaz.tmmas.personnesservice.application.security.handler.JwtAuthentificationFailureHandler;
import ch.globaz.tmmas.personnesservice.application.security.provider.LoginAuthenticationProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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


    @Autowired
    private  AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtAuthenticationSuccessHandler successHandler;
    @Autowired
    private JwtAuthentificationFailureHandler failureHandler;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;


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
        //auth.authenticationProvider(jwtAuthenticationProvider);
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
                //.addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,
                //        API_ROOT_URL), UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();



    }

    //private Filter buildJwtTokenAuthenticationProcessingFilter(List<String> permitAllEndpointList, String apiRootUrl) {
    //}

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
