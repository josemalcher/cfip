package digytal.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import open.digytal.controle.Controle;

public abstract class Resource implements Controle {
	//depois System.getProperty
	protected final String URL="http://localhost:8080/webapi";
    protected abstract ParameterizedTypeReference getListarTodosType();
    protected abstract ParameterizedTypeReference getEntidadeType();
    
	public <T> T incluir(Object entidade) {
		// TODO Auto-generated method stub
		return null;
	}
	public <T> T buscar(Class classe, Object id) {
		return null;
	}
	@Override
	public <T> List<T> listarTodos(Class classe) {
		return getLista(getListarTodosType(), getUri(classe));
	}
	private String getUri(Class classe){
        return classe.getSimpleName().toLowerCase()+"s";
    }
	private String getUrl(Object ... path){
        StringBuilder sufix=new StringBuilder();
        Arrays.asList(path).forEach(item->sufix.append("/").append(item.toString()));
        String url=String.format("%s%s",URL,sufix);
        return url;
    }
	protected List getLista(ParameterizedTypeReference type, Object ... path) {
        RestTemplate rest = new RestTemplate();
        String url =getUrl(path);
        ResponseEntity<List> resposta = rest.exchange(url,HttpMethod.GET, null, type);
        List lista = resposta.getBody();
        return lista;
    }
	protected <T> T getEntidade(ParameterizedTypeReference type, Object ... path) {
        RestTemplate rest = new RestTemplate();
        String url =getUrl(path);
        ResponseEntity<T> resposta = rest.exchange(url,HttpMethod.GET, null, type);
        Object entidade = resposta.getBody();
        return (T) entidade;
    }

}
