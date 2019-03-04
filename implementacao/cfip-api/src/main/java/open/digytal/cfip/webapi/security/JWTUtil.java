package open.digytal.cfip.webapi.security;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	// EXPIRATION_TIME = 1 dia
	static final long EXPIRATION_TIME = 1 * 24 * 60 * 60 * 1000L;
	static final String SECRET = "JWTSecret";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	public static String token(String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		return JWT;
	}
}
