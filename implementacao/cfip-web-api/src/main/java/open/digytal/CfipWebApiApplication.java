package open.digytal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableAutoConfiguration
public class CfipWebApiApplication { // extends SpringBootServletInitializer  extends War File
	//https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
	////https://springframework.guru/spring-requestmapping-annotation/
	//https://www.baeldung.com/spring-data-jpa-query
	
	/*	cd C:\opendigytal\cfip\implementacao\cfip-web-api
	 *  java -jar target\cfip-web-api.jar
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(CfipWebApiApplication.class, args);
	}
}
