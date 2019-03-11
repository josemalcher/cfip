package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;

public class RepositorioVoImpl<T> implements RepositorioVo<T> {
	@PersistenceContext
	private EntityManager em;
	private Class<T> classe;

	@Override
	public List<T> listar() {
		TypedQuery<Tuple> query = em.createQuery("SELECT e.id, e.conta.nome FROM EntidadeLancamento e", Tuple.class);
		List<Tuple> typles = query.getResultList();
		List lista = new ArrayList();
		typles.forEach(tuple -> {
			lista.add(vo(tuple));
		});
		return lista;
	}

	private Object vo(Tuple tuple) {
		try {
			Object vo = classe.newInstance();
			Arrays.asList(getClasse().getDeclaredFields()).forEach(f -> {
				try {
					f.set(vo, tuple.get(0,f.getType()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Class<T> getClasse() {
		return classe;
	}

}
