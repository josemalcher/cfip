package open.digytal;

import java.util.Objects;

import javax.swing.UIManager;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
		// base();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		contexto = builder.run(args);
	}

	private static void initApp(String[] args) {
		DesktopApp.exibirSplash();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		builder.headless(false);
		contexto = builder.run(args);
		LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
		login.exibir();

	}

	private static void base() {
		String FILE_URL = Objects.toString(System.getProperty("db.url"), "file:/opendigytal/cfip/database/cfipdb");
		System.out.println("Iniciando o HSQLDB em " + FILE_URL);
		final String[] dbArg = { "--database.0", FILE_URL, "--dbname.0", "cfipdb", "--port", "5454" };
		org.hsqldb.server.Server.main(dbArg);

		final String[] serveArgs = { "--user", "sa", "--password", "", "--url",
				"jdbc:hsqldb:hsql://localhost:5454/cfipdb" };
		DatabaseManagerSwing.main(serveArgs);
	}

	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
