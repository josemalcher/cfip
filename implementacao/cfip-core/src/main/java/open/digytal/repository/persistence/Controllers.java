package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.StringJoiner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;

import open.digytal.service.Services;
import open.digytal.util.Filtro;

public abstract class Controllers<T> implements Services<T> {
	@PersistenceContext
	protected EntityManager em;
	private Class<T> classe;
	private Class<T> entidade;

	public Controllers() {
		try {
			this.classe = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
			this.entidade = (Class<T>) Class
					.forName(classe.getPackage().getName() + ".Entidade" + classe.getSimpleName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public <T> T buscar(Object id) {
		return (T) em.find(getEntidade(), id);
	}
	@Override
	@Transactional
	public <T> T incluir(T classe){
		Object instancia=null;
		try {
			instancia = entidade.newInstance();
			BeanUtils.copyProperties(classe, instancia);
			em.persist(instancia);
			em.flush();
			em.refresh(instancia);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return (T) instancia;
	}
	@Transactional
	protected <T> T incluirVo(T classe) {
		try {
			Object entity = entidade.newInstance();
			BeanUtils.copyProperties(classe, entity);
			em.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Class<T> getClasse() {
		return classe;
	}

	public Class<T> getEntidade() {
		return entidade;
	}

	private String fields() {
		StringJoiner joiner = new StringJoiner(",", " (", ") ");
		Field[] fields = getClasse().getDeclaredFields();
		for (Field f : fields) {
			joiner.add(" e." + f.getName());
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
		sb.append(getEntidade().getName() + " e ");
		Query query = em.createQuery(sb.toString());
		return query.getResultList();
	}

}
