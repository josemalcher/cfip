package open.digytal;

import java.util.Date;
import java.util.Objects;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.controller.LancamentoController;
import open.digytal.controller.UsuarioGController;
import open.digytal.model.Lancamento;
import open.digytal.model.TipoMovimento;
import open.digytal.service.UsuarioService;
import open.digytal.util.Filtros;

@SpringBootApplication
public class CfipDesktopApp {
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
		base();
		//DesktopApp.exibirSplash();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		builder.headless(false);
		contexto = builder.run(args);
		generic();
		/*LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
		login.exibir();
		*///incluirLancamento();
	}
	private static void generic() {
		UsuarioService service = contexto.getBean(UsuarioService.class);
		service.listar(Filtros.onde("A", "A"));
		System.exit(0);;
	}
	private static void incluirLancamento() {
		Lancamento objeto = new Lancamento();
		objeto.setConta(1);
		objeto.setNatureza(4);
		objeto.setData(new Date());
		objeto.setDescricao("SALARIO");
		objeto.setValor(1000.0);
		objeto.setTipoMovimento(TipoMovimento.C);
		
		//objeto.getParcelamento().setPrimeiraParcela(1);
		//objeto.getParcelamento().setUltimaParcela(1);
		
		LancamentoController ctrl = getBean(LancamentoController.class);
		ctrl.incluir(objeto);
	}
	private static void base() {
		String FILE_URL = Objects.toString(System.getProperty("db.url"), "file:/opendigytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = { "--database.0", FILE_URL, "--dbname.0", "cfipdb", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArg);
		
		final String[] serveArgs = { "--user", "sa", "--password", "", "--url", "jdbc:hsqldb:hsql://localhost:5454/cfipdb"};
		//DatabaseManagerSwing.main(serveArgs);
	}
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
