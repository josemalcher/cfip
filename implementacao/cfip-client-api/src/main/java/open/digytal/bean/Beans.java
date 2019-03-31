package open.digytal.bean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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
}
