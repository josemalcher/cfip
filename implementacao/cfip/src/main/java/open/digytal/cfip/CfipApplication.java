package open.digytal.cfip;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.cfip.dao.ContaDao;
import open.digytal.cfip.model.Conta;
import open.digytal.cfip.model.Lancamento;
import open.digytal.cfip.model.Natureza;
import open.digytal.cfip.model.enums.Categoria;
import open.digytal.cfip.model.enums.TipoMovimento;
import open.digytal.cfip.repository.ContaRepository;
import open.digytal.cfip.repository.LancamentoRepository;
import open.digytal.cfip.repository.NaturezaRepository;

@SpringBootApplication
public class CfipApplication {
	static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		context = SpringApplication.run(CfipApplication.class, args);
		//alteraConta();
		exemploContas();
		exemploNaturezas();
		//incluirLancamento();
		
	}
	static Conta buscarConta(Integer id) {
		ContaRepository repository = context.getBean(ContaRepository.class);
		Optional<Conta> oc = repository.findById(id);
		if(oc.isPresent()) 
			return oc.get();
		else return null;
	}
	static Natureza buscarNatureza(Integer id) {
		NaturezaRepository repository = context.getBean(NaturezaRepository.class);
		Optional<Natureza> oc = repository.findById(id);
		if(oc.isPresent()) 
			return oc.get();
		else return null;
	}
	static void incluirLancamento() {
		Conta conta = buscarConta(1);
		Natureza natureza = buscarNatureza(2);
		if(conta!=null && natureza!=null) {
			Lancamento lancamento = new Lancamento();
			lancamento.setDescricao("SALARIO");
			lancamento.setValor(1250.25D);
			lancamento.setData(new Date());
			
			
			
			lancamento.setConta(conta);
			lancamento.setNatureza(natureza);
			
			LancamentoRepository repository = context.getBean(LancamentoRepository.class);
			repository.save(lancamento);
		}
	}
	static void exemploNaturezas() {
		NaturezaRepository repository = context.getBean(NaturezaRepository.class);
		
		Natureza salario = new Natureza();
		salario.setNome("SALARIO");
		salario.setTipoMovimento(TipoMovimento.C);
		salario.setCategoria(Categoria.S);
		
		repository.save(salario);
		
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
		if(oc.isPresent()) {
			Conta c= oc.get();
			c.setNome("CCR VISA 0701 - 19");
			repository.save(c);
		}
		
	}
	static void exemploContas() {
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

