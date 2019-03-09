package open.digytal.webapi.secutiry;

import org.springframework.security.core.context.SecurityContextHolder;

public class JwtSession {
	public static String getLogin() {
		return  SecurityContextHolder.getContext().getAuthentication().getName();
	}
}
