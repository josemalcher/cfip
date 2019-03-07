package open.digytal.webapi.doc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	//http://www.ciceroednilson.com.br/spring-boot-e-swagger-documentando-e-testando-a-sua-api-rest/
	//http://localhost:8080/cfip-web-api/swagger-ui.html#/
	
	@Bean
	public Docket detalheApi() {
		
		ParameterBuilder paramBuilder = new ParameterBuilder();
		List<Parameter> params = new ArrayList<>();
		paramBuilder.name("Authorization").modelRef(new ModelRef("string"))
		.parameterType("header")
		.required(false)
		.build();
		params.add(paramBuilder.build());
		
		Docket docket = new Docket(DocumentationType.SWAGGER_2);
 
		docket
		.globalOperationParameters(params)
		.select()
		.apis(RequestHandlerSelectors.basePackage("open.digytal.cfip.webapi.resource"))
		.paths(PathSelectors.any())
		.build()
		.apiInfo(this.informacoesApi().build())
		.consumes(new HashSet<String>(Arrays.asList("application/json")))
		.produces(new HashSet<String>(Arrays.asList("application/json")));
		
		return docket;
	}
	
	
	private ApiInfoBuilder informacoesApi() {
 
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
 
		apiInfoBuilder.title("cfip-web-api");
		apiInfoBuilder.description("Api para controle das operações finaceiras");
		apiInfoBuilder.version("1.0");
		apiInfoBuilder.termsOfServiceUrl("Termo de uso: Uso para estudos ou comercialização.");
		apiInfoBuilder.license("Licença - Open Source");
		apiInfoBuilder.licenseUrl("http://www.digytal.com.br");
		apiInfoBuilder.contact(this.contato());
 
		return apiInfoBuilder;
 
	}
	private Contact contato() {
 
		return new Contact(
				"Gleyson Sampaio",
				"http://www.digytal.com.br", 
				"gleyson.sampaio@digytal.com.br");
	}
}