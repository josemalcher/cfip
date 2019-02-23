package open.digytal.cfip;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.cfip.dao.ContaDao;
import open.digytal.cfip.model.Categoria;
import open.digytal.cfip.model.Conta;
import open.digytal.cfip.model.Natureza;
import open.digytal.cfip.model.TipoMovimento;
import open.digytal.cfip.repository.ContaRepository;
import open.digytal.cfip.repository.NaturezaRepository;

@SpringBootApplication
public class CfipApplication {
	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(CfipApplication.class, args);
		//alteraConta();
		exemploContas();
		exemploNaturezas();
		
	}
	static void exemploNaturezas() {
		NaturezaRepository repository = context.getBean(NaturezaRepository.class);
		
		Natureza supermercado = new Natureza();
		supermercado.setNome("SUPERMERCADO");
		supermercado.setTipoMovimento(TipoMovimento.D);
		supermercado.setCategoria(Categoria.A);
		
		repository.save(supermercado);
		
		Natureza restaurante = new Natureza();
		restaurante.setNome("RESTAURANTE");
		restaurante.setTipoMovimento(TipoMovimento.D);
		restaurante.setCategoria(Categoria.A);
		
		System.out.println(restaurante.getCategoria().getNome());
		System.out.println(restaurante.getTipoMovimento().getNome());
		System.out.println("Transferencia" + restaurante.getTipoMovimento().isTranferencia());
		repository.save(restaurante);
		
		imprimirLista(repository.findAll());
		
	}
	static void alteraConta() {
		ContaRepository repository = context.getBean(ContaRepository.class);
		Optional<Conta> oc = repository.findById(2);
		Conta c= oc.get();
		c.setNome("CCR VISA 0701 - 19");
		repository.save(c);
		
	}
	static void exemploContas() {
		ContaDao dao = context.getBean(ContaDao.class);
		//FIXME:Nova implementação
		ContaRepository repository = context.getBean(ContaRepository.class);
		Conta corrente = new Conta();
		corrente.setNome("CCT BRASIL 1879-0");
		corrente.setSaldo(125.85);
		
		dao.salvar(corrente);
		
		Conta cartaoCredito = new Conta();
		cartaoCredito.setPropria(false);
		cartaoCredito.setNome("CCR VISA 0701");
		cartaoCredito.setSaldo(120.0);
		
		dao.salvar(cartaoCredito);
		
		Conta poupanca = new Conta();
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

