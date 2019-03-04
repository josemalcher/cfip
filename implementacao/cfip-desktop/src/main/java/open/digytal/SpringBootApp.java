import java.util.Objects;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.desktop.MDICfip;
import open.digytal.model.Categoria;
import open.digytal.model.Conta;
import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.desktop.DesktopApp;

@SpringBootApplication
public class SpringBootApp {
	static ConfigurableApplicationContext contexto;
	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			init(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	private static void init(String[] args) {
		DesktopApp.exibirSplash();
		String FILE_URL=Objects.toString(System.getProperty("db.url"),"file:/digytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = {"--database.0", FILE_URL, "--dbname.0", "cfipdb","--port","5454"};
		org.hsqldb.server.Server.main(dbArg);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootApp.class);
		builder.headless(false);
		//Todos os componentes estão configurados para um PROFILE
		//builder.profiles(Controle.JPA);
		contexto = builder.run(args);
		//validaConta();
		DesktopApp.fecharSplash();
		MDICfip mdi = SpringBootApp.getBean(MDICfip.class);
		mdi.exibirSessao();
		mdi.setVisible(true);

	}
	static void validaConta() {
		ContaRepository cr = contexto.getBean(ContaRepository.class);
		NaturezaRepository cn = contexto.getBean(NaturezaRepository.class);
		if(cr.listarContas().isEmpty()) {
			Conta carteira = new Conta();
			carteira.setNome("CARTEIRA");
		    carteira.setSigla("CTR");
		    //TESTE
		    carteira.setSaldoInicial(1000.0);
		    carteira.setSaldoAtual(1000.0);
			
			Conta poupanca = new Conta();
			poupanca.setNome("CONTA POUPANCA");
			poupanca.setSigla("CPA");
			poupanca.setSaldoInicial(0.0);
			poupanca.setSaldoAtual(0.0);
			
			Conta corrente = new Conta();
			corrente.setNome("CONTA CORRENTE");
			corrente.setSigla("CCR");
			corrente.setSaldoInicial(0.0);
			corrente.setSaldoAtual(0.0);
			
			Conta cartaoCredito = new Conta();
			cartaoCredito.setNome("CARTAO DE CREDITO");
			cartaoCredito.setSigla("CRD");
			cartaoCredito.setCartaoCredito(true);
			cartaoCredito.setDiaPagamento(20);
			cartaoCredito.setDiaFechamento(10);
			cartaoCredito.setSaldoInicial(1000.0);
			cartaoCredito.setSaldoAtual(1000.0);
			
			Natureza receita = new Natureza();
			receita.setNome("RECEITA");
			receita.setTipoMovimento(TipoMovimento.C);
			receita.setCategoria(Categoria.R);
			
			Natureza despesa = new Natureza();
			despesa.setNome("DESPESA");
			despesa.setTipoMovimento(TipoMovimento.D);
			despesa.setCategoria(Categoria.D);
			
			Natureza transferencia = new Natureza();
			transferencia.setNome("TRANSFERENCIA");
			transferencia.setTipoMovimento(TipoMovimento.T);
			transferencia.setCategoria(Categoria.T);
			
			cr.save(carteira);
			cr.save(corrente);
			cr.save(poupanca);
			cr.save(cartaoCredito);
			
			cn.save(receita);
			cn.save(despesa);
			cn.save(transferencia);
		}
	}
	static void init() {
		MDICfip mdi = contexto.getBean(MDICfip.class);
		mdi.setVisible(true);
	}
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
	
}
