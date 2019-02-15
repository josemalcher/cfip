package open.digytal.cfip;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.cfip.dao.ContaDao;
import open.digytal.cfip.model.Conta;
import open.digytal.cfip.repository.ContaRepository;

@SpringBootApplication
public class CfipApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CfipApplication.class, args);
		ContaDao dao = context.getBean(ContaDao.class);
		//FIXME:Nova implementação
		ContaRepository repository = context.getBean(ContaRepository.class);
		Conta corrente = new Conta();
		corrente.setCartaoCredito(false);
		corrente.setNome("CCT BRASIL 1879-0");
		corrente.setSaldo(125.85);
		
		dao.salvar(corrente);
		
		Conta cartaoCredito = new Conta();
		cartaoCredito.setCartaoCredito(true);
		cartaoCredito.setNome("CCR VISA 0701");
		cartaoCredito.setSaldo(120.0);
		
		dao.salvar(cartaoCredito);
		
		Conta poupanca = new Conta();
		poupanca.setCartaoCredito(false);
		poupanca.setNome("CPC BRASIL 0701-X");
		poupanca.setSaldo(100.0);
		
		//FIXME:Nova implementação
		repository.save(poupanca);
		
		//List<Conta> lista = dao.listar();
		//List<Conta> lista = dao.listarComCriteria();
		List<Conta> lista = repository.findAll();
		
		imprimirLista(lista);
		
	}
	public static void imprimirLista(List lista) {
		for(Object item: lista) {
			System.out.println(item);
		}
	}

}

