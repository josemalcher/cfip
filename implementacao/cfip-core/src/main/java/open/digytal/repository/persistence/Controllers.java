package open.digytal.repository.persistence;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import open.digytal.service.Services;

public abstract class Controllers<T> implements Services<T> {
	@PersistenceContext
	protected EntityManager em;
	private Class<T> classe;
	private String entidade;

	public Controllers() {
		this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		this.entidade = classe.getPackage().getName() + ".Entidade"+classe.getSimpleName();
	}
	
	public Class<T> getClasse() {
		return classe;
	}
	public String getEntidade() {
		return entidade;
	}
	
	
	/*public List<T> listar( Filtro... filtros) {
		return listar(Arrays.asList(filtros));
	}
	
	@Override
	public List<T> listar(List<Filtro> filtros) {
		JPAQL jpaql = new JPAQL(em, classe);
		jpaql.setFiltros(filtros);
		List<EntidadeUsuario> lista= jpaql.listar();
		return null;
	}*/

}
