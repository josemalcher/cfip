package open.digytal;

import javax.swing.UIManager;

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
			initApp(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	private static void initApp(String[] args) {
		DesktopApp.exibirSplash();
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
		builder.headless(false);

		contexto = builder.run(args);
		//contexto.getEnvironment().setActiveProfiles(Services.API);
		LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
		login.exibir();

	}

	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
