package ch.globaz.tmmas.zuulapigateway.application.security.handler;

import ch.globaz.tmmas.zuulapigateway.application.api.resources.common.ErrorCode;
import ch.globaz.tmmas.zuulapigateway.application.api.resources.common.ErrorMessage;
import ch.globaz.tmmas.zuulapigateway.application.api.resources.common.ErrorResponseResource;
import ch.globaz.tmmas.zuulapigateway.application.security.exception.AuthentificationMethodNotSupportedException;
import ch.globaz.tmmas.zuulapigateway.application.security.exception.JwtExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthentificationFailureHandler implements AuthenticationFailureHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthentificationFailureHandler.class);

    private final ObjectMapper mapper;

    @Autowired
    public AuthentificationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {

        LOGGER.warn("Authentification failure: {}",e.getMessage());

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,
                    ErrorMessage.INVALID_USERNAME_OR_PASSWORD,
                    e.getMessage(), ErrorCode.AUTHENTICATION ));

        } else if (e instanceof JwtExpiredTokenException) {

            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,
                    ErrorMessage.JWT_TOKEN_EXPIRED,
                    e.getMessage(),ErrorCode.JWT_TOKEN_EXPIRED));

        } else if (e instanceof AuthentificationMethodNotSupportedException) {

            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,
                    ErrorMessage.AUTH_METHOD_NOT_SUPPORTED,
                    e.getMessage(), ErrorCode.AUTHENTICATION ));
        }


        mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,
                ErrorMessage.AUTH_FAILED,
                e.getMessage(),ErrorCode.AUTHENTICATION ));
    }
}
