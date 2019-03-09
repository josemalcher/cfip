package open.digytal.repository.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import open.digytal.model.acesso.EntidadeUsuario;
import open.digytal.service.Services;
import open.digytal.util.Filtro;
import open.digytal.util.JPAQL;

public abstract class Controllers<T> implements Services<T> {
	@PersistenceContext
	private EntityManager em;
	private Class<T> entityClass;

	public Controllers() {
		this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public List<T> listar( Filtro... filtros) {
		return listar(Arrays.asList(filtros));
	}

	
	@Override
	public List<T> listar(List<Filtro> filtros) {
		JPAQL jpaql = new JPAQL(em, entityClass);
		jpaql.setFiltros(filtros);
		List<EntidadeUsuario> lista= jpaql.listar();
		return null;
	}

}
