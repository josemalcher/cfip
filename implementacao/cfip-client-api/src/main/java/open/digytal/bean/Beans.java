package open.digytal.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import open.digytal.model.Sessao;

@Configuration
public class Beans {
	@Bean
	public Sessao sessao() {
		return new Sessao();
	}
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
