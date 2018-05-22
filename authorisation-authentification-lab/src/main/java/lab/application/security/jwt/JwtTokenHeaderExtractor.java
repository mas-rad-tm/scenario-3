package lab.application.security.jwt;


import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * Implémentation de {@link TokenExtractor}
 * Extraction du token jwt depuis le header http suivant:
 * Authorization: Bearer
 */
@Component
public class JwtTokenHeaderExtractor implements TokenExtractor {

    public static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {

        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length());
    }

}
