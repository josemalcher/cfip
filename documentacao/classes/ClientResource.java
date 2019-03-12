package open.digytal.resource.client;

import open.digytal.controle.Controle;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public abstract class ClientResource implements Controle {
    //depois System.getProperty - https://howtodoinjava.com/spring-restful/spring-restful-client-resttemplate-example/
    protected final String URL = "http://localhost:8080/cfip-webapi";

    protected abstract ParameterizedTypeReference getListaType();

    protected abstract ParameterizedTypeReference getEntidadeType();

    public <T> T incluir(Object entidade) {
        // TODO Auto-generated method stub
        return null;
    }
    public <T> T alterar(Object entidade) {
        // TODO Auto-generated method stub
        return null;
    }
    public <T> T buscar(Class classe, Object id) {
        return null;
    }

    @Override
    public <T> List<T> listarTodos(Class classe) {
        return getLista(getListaType(), getUri(classe));
    }

    private String getUri(Class classe) {
        return classe.getSimpleName().toLowerCase() + "s";
    }

    private String getUrl(Object... path) {
        StringBuilder sufix = new StringBuilder();
        Arrays.asList(path).forEach(item -> sufix.append("/").append(item.toString()));
        String url = String.format("%s%s", URL, sufix);
        System.out.println(url);
        return url;
    }

    protected <T> T post(Object entidade, Object... path){
        System.out.println("CHAMANDO A API REST");

        String url = getUrl(path);
        RestTemplate restTemplate = new RestTemplate();
        Object result = restTemplate.postForObject( url, entidade, entidade.getClass());
        return (T) result;
    }
    protected <T> T get(ParameterizedTypeReference type, Object... path) {
        System.out.println("CHAMANDO A API REST");
        RestTemplate rest = new RestTemplate();
        String url = getUrl(path);
        ResponseEntity<T> resposta = rest.exchange(url, HttpMethod.GET, null, type);
        Object entidade = resposta.getBody();
        return (T) entidade;
    }
    protected List getLista(ParameterizedTypeReference type, Object... path) {
        System.out.println("CHAMANDO A API REST");
        RestTemplate rest = new RestTemplate();
        String url = getUrl(path);
        ResponseEntity<List> resposta = rest.exchange(url, HttpMethod.GET, null, type);
        List lista = resposta.getBody();
        return lista;
    }

}