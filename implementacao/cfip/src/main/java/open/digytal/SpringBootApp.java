package open.digytal;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.desktop.cfip.MDICfip;
import open.digytal.model.Categoria;
import open.digytal.model.Conta;
import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.util.desktop.DesktopApp;

@SpringBootApplication
//@EnableJpaRepositories
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
		SpringApplicationBuilder builder = new SpringApplicationBuilder(SpringBootApp.class);
		builder.headless(false);
		//Todos os componentes estão configurados para um PROFILE
		//builder.profiles(Controle.JPA);
		contexto = builder.run(args);
		validaConta();
		DesktopApp.fecharSplash();
		MDICfip mdi = SpringBootApp.getBean(MDICfip.class);
		mdi.exibirSessao();
		mdi.setVisible(true);

	}
	static void validaConta() {
		ContaRepository cr = contexto.getBean(ContaRepository.class);
		NaturezaRepository cn = contexto.getBean(NaturezaRepository.class);
		if(cr.listar().isEmpty()) {
			Conta conta = new Conta();
			conta.setNome("BB 78595");
			conta.setSigla("BB 78595");
			conta.setPropria(true);
			
			Natureza receita = new Natureza();
			receita.setNome("RECEITA");
			receita.setTipoMovimento(TipoMovimento.C);
			receita.setCategoria(Categoria.R);
			
			Natureza despesa = new Natureza();
			despesa.setNome("DESPESA");
			despesa.setTipoMovimento(TipoMovimento.D);
			despesa.setCategoria(Categoria.D);
			
			cr.save(conta);
			cn.save(receita);
			cn.save(despesa);
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

