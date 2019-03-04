package open.digytal.cfip.webapi.resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.cfip.webapi.security.JWTUser;

@RestController
@RequestMapping("/login")
public class LoginResource {
	@PostMapping
	public String login(@RequestBody JWTUser user) {
		//{"login":"admin","senha":"pass"}
		return "";
	}
}
