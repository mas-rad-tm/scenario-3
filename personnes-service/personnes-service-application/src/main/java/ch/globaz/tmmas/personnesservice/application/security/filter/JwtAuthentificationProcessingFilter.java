package ch.globaz.tmmas.personnesservice.application.security.filter;

import ch.globaz.tmmas.personnesservice.application.security.configuration.SecurityConfiguration;
import ch.globaz.tmmas.personnesservice.application.security.jwt.JwtAuthenticationToken;
import ch.globaz.tmmas.personnesservice.application.security.jwt.RawAccessJwtToken;
import ch.globaz.tmmas.personnesservice.application.security.jwt.TokenExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtre actif sur toutes les url définis comme devant être sécurisée.
 * S'assure que le token est bien présent dans l'en tête http, et transfert le traitement
 * d'authentification au manager concerné
 *
 */
public class JwtAuthentificationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationFailureHandler failureHandler;

    private final TokenExtractor tokenExtractor;

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentificationProcessingFilter.class);

    @Autowired
    public JwtAuthentificationProcessingFilter(AuthenticationFailureHandler failureHandler,
                                               TokenExtractor tokenExtractor, RequestMatcher matcher) {
        super(matcher);
        this.failureHandler = failureHandler;
        this.tokenExtractor = tokenExtractor;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String tokenPayload = request.getHeader(SecurityConfiguration.AUTHENTICATION_HEADER_NAME);
        RawAccessJwtToken token = new RawAccessJwtToken(tokenExtractor.extract(tokenPayload));

        logger.info("Authentification JwtRequestFilter, payload: {}, token:{}",tokenPayload,token);

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }
}
