package open.digytal.cfip;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.cfip.model.Conta;

@SpringBootApplication
public class CfipApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CfipApplication.class, args);
		
		Conta corrente = new Conta();
		corrente.setCartaoCredito(false);
		corrente.setNome("CCT BRASIL 1879-0");
		corrente.setSaldo(125.85);
		
		Conta cartaoCredito = new Conta();
		cartaoCredito.setCartaoCredito(true);
		cartaoCredito.setNome("CCR VISA 0701");
		cartaoCredito.setSaldo(120.0);
		
		
	}
	public static void imprimirLista(List lista) {
		for(Object item: lista) {
			System.out.println(item);
		}
	}

}

