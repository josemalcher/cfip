package open.digytal.cfip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import open.digytal.cfip.controle.Calculadora;

@SpringBootApplication
public class CfipApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CfipApplication.class, args);
	
		Calculadora calc =context.getBean(Calculadora.class);
		
		System.out.println("Teste com Spring " + calc.somar(1, 8));
	}
	

}

