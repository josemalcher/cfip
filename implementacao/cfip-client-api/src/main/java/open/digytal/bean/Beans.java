package open.digytal.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import open.digytal.model.Sessao;

@Configuration
public class Beans {
	@Bean
	public Sessao sessao() {
		return new Sessao();
	}
}
