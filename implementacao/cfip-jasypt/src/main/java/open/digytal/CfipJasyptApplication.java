package open.digytal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CfipJasyptApplication {

	private static ConfigurableApplicationContext run;

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", "password");
		run = SpringApplication.run(CfipJasyptApplication.class, args);
		PropertyServiceForJasyptStarter service = run.getBean(PropertyServiceForJasyptStarter.class);
		System.out.println(service.getProperty());
	}

}
