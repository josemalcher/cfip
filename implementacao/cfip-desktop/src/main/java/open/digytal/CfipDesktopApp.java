package open.digytal;

import java.util.List;
import java.util.Objects;

import javax.swing.UIManager;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.model.Lancamentos;
import open.digytal.service.LancamentoService;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.LoginPanel;

@SpringBootApplication
public class CfipDesktopApp {
	static ConfigurableApplicationContext contexto;

	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			initMain(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void initMain(String[] args) {
		//base();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		contexto = builder.run(args);
		LancamentoService service = contexto.getBean(LancamentoService.class);
		List<Lancamentos> lista = service.listarVo("gso", 1, 1);
		lista.forEach(i->{System.out.println(i);});
		//persistencia();
	}
	private static void initApp(String[] args) {
		DesktopApp.exibirSplash();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		builder.headless(false);
		contexto = builder.run(args);
		LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
		login.exibir();
		
	}

	/*
	 * private static void persistencia() { UsuarioService service =
	 * contexto.getBean(UsuarioService.class); Usuario user =
	 * service.buscar("teste"); if(user==null) { user = new Usuario();
	 * user.setLogin("teste"); user.setSenha("teste");
	 * user.setEmail("gso@gso.com.br"); user.setNome("GLEYSON SAMPAIO");
	 * service.incluir(user); }else { List<Usuario> lista=
	 * service.listar(Filtros.onde("nome", "GLEYSON"));
	 * lista.forEach(i->{System.out.println(i);}); } System.exit(0);; } private
	 * static void incluirLancamento() { EntidadeLancamento objeto = new
	 * EntidadeLancamento(); objeto.setConta(1); objeto.setNatureza(4);
	 * objeto.setData(new Date()); objeto.setDescricao("SALARIO");
	 * objeto.setValor(1000.0); objeto.setTipoMovimento(TipoMovimento.C);
	 * 
	 * //objeto.getParcelamento().setPrimeiraParcela(1);
	 * //objeto.getParcelamento().setUltimaParcela(1);
	 * 
	 * LancamentoController ctrl = getBean(LancamentoController.class);
	 * ctrl.incluir(objeto); }
	 */
	private static void base() {
		String FILE_URL = Objects.toString(System.getProperty("db.url"), "file:/opendigytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = { "--database.0", FILE_URL, "--dbname.0", "cfipdb", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArg);
		
		final String[] serveArgs = { "--user", "sa", "--password", "", "--url", "jdbc:hsqldb:hsql://localhost:5454/cfipdb"};
		DatabaseManagerSwing.main(serveArgs);
	}
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
