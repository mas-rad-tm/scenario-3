package ch.globaz.tmmas.personnesservice.application.security.handler;

import ch.globaz.tmmas.personnesservice.application.api.web.resources.common.ErrorResponseResource;
import ch.globaz.tmmas.personnesservice.application.common.ErrorCode;
import ch.globaz.tmmas.personnesservice.application.security.exception.AuthentificationMethodNotSupportedException;
import ch.globaz.tmmas.personnesservice.application.security.exception.JwtExpiredTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthentificationFailureHandler implements AuthenticationFailureHandler{

    private final ObjectMapper mapper;

    @Autowired
    public AuthentificationFailureHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        if (e instanceof BadCredentialsException) {
            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,
                    "Invalid username or password",e.getMessage(), ErrorCode.AUTHENTICATION ));
        } else if (e instanceof JwtExpiredTokenException) {
            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,"Token has expired",
                    e.getMessage(),ErrorCode.JWT_TOKEN_EXPIRED));
        } else if (e instanceof AuthentificationMethodNotSupportedException) {
            mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,"Athu metod not supported",
                    e.getMessage(), ErrorCode.AUTHENTICATION ));
        }

        mapper.writeValue(response.getWriter(), new ErrorResponseResource(HttpStatus.UNAUTHORIZED,"Authentication failed",
                e.getMessage(),ErrorCode.AUTHENTICATION ));
    }
}
