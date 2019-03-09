package open.digytal.repository.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import open.digytal.service.Services;
import open.digytal.util.Filtro;

public class Controllers<T> implements Services<T> {
	@PersistenceContext
	private EntityManager em;
	private Class entityClass;

	public Controllers() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	@Override
	public <T> List<T> listar(Filtro... filtros) {
		System.out.println(entityClass);
		return null;
	}

}
