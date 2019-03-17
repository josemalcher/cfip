package open.digytal;

import java.io.File;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.LoginPanel;

@SpringBootApplication
public class CfipDesktopApp {
	private static ConfigurableApplicationContext contexto;
	private static String home=System.getProperty("user.dir");
	public static void main(String[] args) {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(lf);
			environment();
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
		LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
		login.exibir();
		
	}
	private static void environment() {
		try {
			System.out.println("Definindo o app.home=" + home);
			System.setProperty("app.home", home);
			File config = new File(home,"config.properties");
			if(!config.exists())
				config.createNewFile();
		}catch (Exception e) {
			e.printStackTrace();
		}
			
	}
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
