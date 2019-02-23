package open.digytal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootApp {
	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(SpringBootApp.class, args);
	}
	public static <T> T getBean(Class classe) {
		return (T) context.getBean(classe);
	}
	
}

