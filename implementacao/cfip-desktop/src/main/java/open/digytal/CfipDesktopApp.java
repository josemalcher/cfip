package open.digytal;

import javax.swing.UIManager;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import open.digytal.util.desktop.Configuracao;
import open.digytal.util.desktop.DesktopApp;
import open.digytal.util.desktop.FrmConfiguracao;
import open.digytal.util.jasypt.JasyptTest;

@SpringBootApplication
@EnableEncryptableProperties
public class CfipDesktopApp extends DesktopApp {
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
	private static void jasyptTest() {
		//http://www.jasypt.org/encrypting-texts.html
		/*
		 * BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		 * textEncryptor.setPassword("cfip");
		 * 
		 * System.out.println(textEncryptor.encrypt(
		 * "http://www.boxsolutions.com.br/cfip-web-api/"));
		 * 
		 * StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		 * encryptor.setPassword("cfip");
		 * 
		 * //System.out.println(encryptor.encrypt(
		 * "www.boxsolutions.com.br/cfip-web-api/"));
		 */		
		
		
		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
		String privateData = "www.boxsolutions.com.br/cfip-web-api/";
		textEncryptor.setPasswordCharArray("cfip".toCharArray());
		
		System.out.println(textEncryptor.encrypt(privateData));
		
		JasyptTest jasypt = contexto.getBean(JasyptTest.class);
		System.out.println(jasypt.getUrl());
		
		
	}
	private static void initApp(String[] args) {
		//DesktopApp.exibirSplash();
		if(Configuracao.iniciarConfiguracao()) {
			DesktopApp.fecharSplash();
			FrmConfiguracao.iniciar();
		}else {
			SpringApplicationBuilder builder = new SpringApplicationBuilder(CfipDesktopApp.class);
			builder.headless(false);
			contexto = builder.run(args);
			//LoginPanel login = CfipDesktopApp.getBean(LoginPanel.class);
			//login.exibir();
			jasyptTest();
		}
		
	}
	
	public static <T> T getBean(Class classe) {
		return (T) contexto.getBean(classe);
	}
}
