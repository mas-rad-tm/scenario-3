package lab.application.security.jwt.verifier;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenVerifier implements TokenVerifier {
	@Override
	public boolean verify(String jti) {
		return true;
	}
}
