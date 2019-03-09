package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import open.digytal.model.acesso.Usuario;
import open.digytal.service.Services;
import open.digytal.util.Filtro;

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
	
	private String fields() {
		StringJoiner joiner = new StringJoiner(",", " (", ") ");
		Field[] fields = getClasse().getDeclaredFields();
		for (Field f : fields) {
	        joiner.add(" e."+ f.getName() );
	    }
		return joiner.toString();
	}
	@Override
	public List listar(Filtro... filtros) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT NEW ");
		sb.append(getClasse().getName());
		sb.append(fields());
		sb.append("FROM ");
		sb.append(getEntidade() + " e ");
		Query query = em.createQuery(sb.toString());
		return query.getResultList();
	}

}
