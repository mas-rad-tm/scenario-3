package lab.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.application.api.web.common.JwtSkipPathRequestMatcher;
import lab.application.api.web.rest.RestAuthentificationEntryPoint;
import lab.application.security.JwtTokenProvider;
import lab.application.security.filter.CustomCorsFilter;
import lab.application.security.filter.JwtAuthentificationProcessingFilter;
import lab.application.security.filter.LoginProcessingFilter;
import lab.application.security.jwt.TokenExtractor;
import lab.application.security.provider.JwtAuthenticationProvider;
import lab.application.security.provider.LoginAuthenticationProvider;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Configuration globale de la sécurité de l'application. <br/>
 * - Activation de la sécurité spring por le projet (@EnableWebSecurity). <br/>
 * - Activation de la sécurité au niveau des méthodes (@EnableGlobalMethodSecurity)
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String SIGNUP_URL = "/api/auth/signup";
    public static final String PUBLIC_TEST_URL = "/api/public/test";
    public static final String API_ROOT_URL = "/api/**";

    @Autowired private RestAuthentificationEntryPoint authenticationEntryPoint;
    @Autowired private AuthenticationSuccessHandler successHandler;
    @Autowired private AuthenticationFailureHandler failureHandler;

    @Autowired private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired private LoginAuthenticationProvider loginAuthenticationProvider;


    @Autowired private TokenExtractor tokenExtractor;

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private ObjectMapper objectMapper;

    /**
     * Fourni une instance du filtre de traitement des authentifications de login
     * @param loginEntryPoint l'url de la ressource POST de login
     * @throws Exception
     */
    protected LoginProcessingFilter buildLoginProcessingFilter(String loginEntryPoint) throws Exception {
        LoginProcessingFilter filter = new LoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
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



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(loginAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        List<String> permitAllEndpointList = Arrays.asList(
                AUTHENTICATION_URL,
                REFRESH_TOKEN_URL,
                SIGNUP_URL,
                PUBLIC_TEST_URL,
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
                .csrf().disable() // We don't need CSRF for JWT based authentication

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
                .antMatchers(API_ROOT_URL).authenticated() // Protected API End-points
                .and()
                .addFilterBefore(new CustomCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(permitAllEndpointList,
                        API_ROOT_URL), UsernamePasswordAuthenticationFilter.class)
                .headers().frameOptions().disable();



    }




}
