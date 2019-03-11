package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import open.digytal.util.Filtro;

@Repository
public class RepositorioVoImpl implements RepositorioVo {
	@PersistenceContext
	private EntityManager em;
	private Class classe;
	private String sql;
	private Filtro[] filtros;

	@Override
	public List listar(Class classe, List<Filtro> filtros) {
		return listar(classe, null,filtros);
	}
	@Override
	public List listar(Class classe, String sql,List<Filtro> filtros) {
		return listar(classe,sql, filtros.stream().toArray(Filtro[]::new));
	}
	
	@Override
	public List listar(Class classe, Filtro... filtros) {
		return listar(classe, null, filtros);
	}
	@Override
	public List listar(Class classe, String sql,Filtro... filtros) {
		this.classe=classe;
		this.sql=sql;
		if (classe.isAnnotationPresent(Entity.class))
			return listarEntidade(filtros);
		else
			return listarVo(filtros);

	}

	private List listarEntidade(Filtro... filtros) {
		this.filtros = filtros;
		Query query = em.createQuery(getSql());
		param(query);
		return query.getResultList();
	}

	private List listarVo(Filtro... filtros) {
		this.filtros = filtros;
		TypedQuery<Tuple> query = em.createQuery(getSql(), Tuple.class);
		param(query);
		List<Tuple> typles = query.getResultList();
		List lista = new ArrayList();
		typles.forEach(tuple -> {
			lista.add(convertToVo(tuple));
		});
		return lista;
	}

	private void param(Query query) {
		if (filtros.length > 0) {
			for (Filtro f : filtros) {
				if (!f.isOrdem() && !f.isTodos())
					query.setParameter(f.getParametro(), f.getValor());
			}
		}
	}

	private String getSql() {
		StringBuilder sql = new StringBuilder(Objects.toString(this.sql, "SELECT e FROM " + classe + " e "));
		if (filtros.length > 0) {
			for (Filtro f : filtros) {
				if (!f.isOrdem() && !f.isTodos()) {
					String append = String.format(" %s e.%s %s :%s", f.getJuncao(), f.getCampo(), f.getOperador(),
							f.getParametro());
					sql.append(append);
				} else {
					sql.append(" ORDER BY " + f.getCampo());
				}
			}
		}
		return sql.toString();
	}

	private Object convertToVo(Tuple tuple) {
		try {
			Object instance = classe.newInstance();
			for (TupleElement e : tuple.getElements()) {
				Field field = classe.getDeclaredField(e.getAlias());
				field.setAccessible(true);
				field.set(instance, tuple.get(e.getAlias(), field.getType()));
			}
			return instance;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
