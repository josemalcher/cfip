package open.digytal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("file:${app.home}/config.properties")
public class Config {

}
