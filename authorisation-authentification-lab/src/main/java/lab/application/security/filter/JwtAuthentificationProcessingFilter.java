package lab.application.security.filter;

import lab.application.SecurityConfiguration;
import lab.application.security.JwtAuthenticationToken;
import lab.application.security.jwt.RawAccessJwtToken;
import lab.application.security.jwt.TokenExtractor;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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

    /*
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        logger.info("JwtAuthentificationFilter#doFilterInternal, request: {}",request.getServletPath());

        try {
            String jwt = getJwtFromRequest(request);

            logger.info("jwt from request: {}",jwt);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {

                logger.info("JwtAuthentificationFilter#doFilterInternal, token valid!");

                //Long userId = tokenProvider.getUserIdFromJWT(jwt);
                UserDetails userDetails = utilisateurDetailService.getUserFromToken(jwt);
                /*
                    Note that you could also encode the user's nomUtilisateur and roles inside JWT claims
                    and create the UserDetails object by parsing those claims from the JWT.
                    That would avoid the following database hit. It's completely up to you.
                 */
    /*
                //UserDetails userDetails = utilisateurDetailService.loadUserByUId(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }
*/

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
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
