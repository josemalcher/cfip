package open.digytal.client.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.DatatypeConverter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import open.digytal.model.Sessao;
import open.digytal.util.Texto;
public abstract class ClientResource {
	private static final Logger logger = LogManager.getLogger(ClientResource.class);
	@Value("${api.url}")
	private String url;
	@Autowired	
	protected Sessao sessao;
	private String getUrl(Serializable... path) {
		String ROOT=Objects.toString(url,"http://localhost:8080/");
		String sufix = Texto.concatenar("/", path);
		String url = String.format("%s%s", ROOT, sufix);
		return url;
	}	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				if (sessao!=null && sessao.getToken()!=null ) {
					request.getHeaders().set("Authorization",sessao.getToken());
				}

				return execution.execute(request, body);
			}
		});
		return restTemplate;
	}

	protected <T> T post(Object request, Serializable... path) {
		return post(request.getClass(), request, path);
	}

	protected <T> T post(Class response, Object request, Serializable... path) {
		String url = getUrl(path);
		logger.info("POST --> " + url);
		Object result = getRestTemplate().postForObject(url, request, response);
		return (T) result;
	}

	protected <T> T put(Object entidade, Serializable... path) {
		String url = getUrl(path);
		logger.info("PUT --> " + url);
		getRestTemplate().put(url, entidade);
		return (T) entidade;
	}

	protected <T> T get(ParameterizedTypeReference type, Serializable... path) {
		String url = getUrl(path);
		logger.info("GET --> " + url);
		ResponseEntity<T> resposta = getRestTemplate().exchange(url, HttpMethod.GET, null, type);
		Object entidade = resposta.getBody();
		return (T) entidade;
	}

	protected List getLista(ParameterizedTypeReference type, Serializable... path) {
		String url = getUrl(path);
		logger.info("GET LISTA --> " + url);
		ResponseEntity<List> resposta = getRestTemplate().exchange(url, HttpMethod.GET, null, type);
		List lista = resposta.getBody();
		return lista;
	}
	public Claims getClaims(String jwt) {
		//TODO: KEY PARAM
	    Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary("CfipWebApiSecret")).parseClaimsJws(jwt).getBody();
	    return claims;
	}
	/*
	 * @Override public <T> T incluir(Object entidade) { return post(entidade); }
	 * 
	 * @Override public <T> T alterar(Object entidade) { return put(entidade); }
	 * 
	 * @Override public <T> T buscar(Class classe, Serializable id) { return
	 * get(getEntidadeType(),id); }
	 */
}
