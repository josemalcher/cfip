package open.digytal.cfip.webapi.security;

import static open.digytal.cfip.webapi.security.JWTUtil.HEADER_STRING;
import static open.digytal.cfip.webapi.security.JWTUtil.SECRET;
import static open.digytal.cfip.webapi.security.JWTUtil.TOKEN_PREFIX;
import static open.digytal.cfip.webapi.security.JWTUtil.token;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Jwts;
public class TokenAuthenticationService {
	//https://medium.com/@hantsy/protect-rest-apis-with-spring-security-and-jwt-5fbc90305cc5
	
	/*@Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";
    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000; // 1h
*/	
	/*@PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }*/
	static void addAuthentication(HttpServletResponse response, String username) {
		response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + token(username));
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody().getSubject();
			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
			}
		}
		return null;
	}
}
