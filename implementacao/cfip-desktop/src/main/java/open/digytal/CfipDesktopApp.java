package open.digytal;

import javax.swing.UIManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import open.digytal.util.config.Configuracao;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.FrmConfiguracao;
import open.digytal.util.desktop.LoginPanel;

@SpringBootApplication
@EnableEncryptableProperties

//http://mbcoder.com/spring-boot-how-to-encrypt-properties-in-application-properties/
//https://stackoverflow.com/questions/37404703/spring-boot-how-to-hide-passwords-in-properties-file
//https://howtodoinjava.com/maven/create-windows-exe-file-for-java-application/
//http://trabajosdesisifo.blogspot.com/2015/12/java-bundle-jre-inside-executable-file.html
//java -jar -Dspring.profiles.active=prod spring-boot-demo.jar
//https://www.tutorialspoint.com/log4j/log4j_logging_levels.htm
//https://www.callicoder.com/spring-boot-log4j-2-example/
//http://www.iconarchive.com/show/free-shopping-icons-by-petalart/money-wallet-icon.html
//https://www.youtube.com/watch?v=BKn5DxLtv78
//http://www.boxsolutions.com.br/cfip-web-api/
public class CfipDesktopApp extends DesktopApp {
	private static final Logger logger = LogManager.getLogger(CfipDesktopApp.class);
	private static ConfigurableApplicationContext contexto;
	
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
		if(Configuracao.iniciarConfiguracao()) {
			DesktopApp.fecharSplash();
			FrmConfiguracao.iniciar();
		}else {
			SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
			builder.headless(false);
			contexto = builder.run(args);
			LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
			login.exibir();
		}
		logger.info("Bem vindo");
		
	}
	
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
