package lab.application.security.jwt.verifier;

public interface TokenVerifier {
	public boolean verify(String jti);
}
