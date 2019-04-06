package open.digytal.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
public class Beans {
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}
}
