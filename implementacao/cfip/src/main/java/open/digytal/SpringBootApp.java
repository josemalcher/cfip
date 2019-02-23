package open.digytal;

import javax.swing.UIManager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import open.digytal.desktop.cfip.MDICfip;
import open.digytal.util.desktop.DesktopApp;

@SpringBootApplication
@EnableJpaRepositories
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
		DesktopApp.fecharSplash();
		MDICfip mdi = SpringBootApp.getBean(MDICfip.class);
		mdi.exibirSessao();
		mdi.setVisible(true);

	}
	static void init() {
		MDICfip mdi = contexto.getBean(MDICfip.class);
		mdi.setVisible(true);
	}
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
	
}

