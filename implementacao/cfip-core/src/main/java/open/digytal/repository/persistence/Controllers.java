package open.digytal.repository.persistence;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import open.digytal.service.Services;
import open.digytal.util.Filtro;

public abstract class  Controllers<T> implements Services<T>  {
	@PersistenceContext
	private EntityManager em;
	private Class entityClass;
	public Controllers() {
		this.entityClass = (Class<T>) ((ParameterizedType) 
			      getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/*public Controllers(Class entityClass) {
		this.entityClass=entityClass;
	}*/

	//@Override
	public <T> List<T> listar(Filtro... filtros) {
		System.out.println(entityClass);
		return null;
	}

}
