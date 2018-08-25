package ch.globaz.tmmas.zuulapigateway.application.security.handler;

import ch.globaz.tmmas.zuulapigateway.application.security.jwt.JwtToken;
import ch.globaz.tmmas.zuulapigateway.application.security.jwt.JwtTokenFactory;
import ch.globaz.tmmas.zuulapigateway.application.security.model.ContexteUtilisateur;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * AjaxAwareAuthenticationSuccessHandler
 * 
 * @author vladimir.stankovic
 *
 *         Aug 3, 2016
 */
@Component
public class AuthentificationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;
    private final JwtTokenFactory tokenFactory;

    @Autowired
    public AuthentificationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory, final ApplicationEventPublisher eventPublisher) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        ContexteUtilisateur contexteUtilisateur = (ContexteUtilisateur) authentication.getPrincipal();
        
        JwtToken accessToken = tokenFactory.createAccessJwtToken(contexteUtilisateur);
        JwtToken refreshToken = tokenFactory.createRefreshToken(contexteUtilisateur);
        
        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("refreshToken", refreshToken.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getWriter(), tokenMap);

        clearAuthenticationAttributes(request);

    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     * 
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
