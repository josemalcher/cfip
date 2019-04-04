package open.digytal.bean;

import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;

import open.digytal.model.Sessao;
@Configuration
public class Beans {
	@Bean
	@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
	public Sessao sessao() {
		return new Sessao();
	}
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	@Bean
	public BasicTextEncryptor basicTextEncryptor() {
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPasswordCharArray("CfipAppSecret".toCharArray());
		return encryptor;
	}
}
