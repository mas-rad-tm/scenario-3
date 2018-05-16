package lab.application;

import lab.application.security.JwtAuthentificationEntryPoint;
import lab.application.security.JwtAuthentificationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    UtilisateurDetailService utilisateurDetailService;

    @Autowired
    private JwtAuthentificationEntryPoint unauthorizedHandler;

    @Bean
    public JwtAuthentificationFilter jwtAuthenticationFilter() {
        return new JwtAuthentificationFilter();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(utilisateurDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * https://stackoverflow.com/questions/24364436/adding-a-custom-filter-to-be-invoked-after-spring-security-filter-in-a-servlet-3/37904857#37904857
     * @param jwtAuthentificationFilter
     * @return
     */
    @Bean
    public  FilterRegistrationBean jwtAuthentificationFilterRegistration (JwtAuthentificationFilter jwtAuthentificationFilter) {
        FilterRegistrationBean b = new FilterRegistrationBean();
        b.setFilter(jwtAuthentificationFilter);
        b.setEnabled(Boolean.FALSE);
        return b;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/h2/**")
                .permitAll()
                .antMatchers("/api/auth/**")
                .permitAll()
                .antMatchers("/api/public/**")
                .permitAll()
                .anyRequest()
                .authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();
        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

}
