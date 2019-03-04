package open.digytal.cfip.webapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class JWTApplication {
	//https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
	////https://springframework.guru/spring-requestmapping-annotation/
	public static void main(String[] args) {
		SpringApplication.run(JWTApplication.class, args);
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
}
