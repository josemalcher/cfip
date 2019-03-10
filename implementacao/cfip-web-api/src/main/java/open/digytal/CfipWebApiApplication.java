package open.digytal;

import java.util.Objects;

import org.hsqldb.util.DatabaseManagerSwing;
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
	public static void main(String[] args) {
		SpringApplication.run(CfipWebApiApplication.class, args);
	}
	private static void base() {
		String FILE_URL = Objects.toString(System.getProperty("db.url"), "file:/opendigytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = { "--database.0", FILE_URL, "--dbname.0", "cfipdb", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArg);
		
		final String[] serveArgs = { "--user", "sa", "--password", "", "--url", "jdbc:hsqldb:hsql://localhost:5454/cfipdb"};
		DatabaseManagerSwing.main(serveArgs);
	}
}
