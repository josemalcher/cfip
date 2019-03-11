package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
@Repository
public class RepositorioVoImpl implements RepositorioVo {
	@PersistenceContext
	private EntityManager em;
	private Class vo;
	@Override
	public List listar(Class vo,String sql, Map<String,Object> params) {
		this.vo=vo;
		TypedQuery<Tuple> query = em.createQuery("SELECT e.conta.nome as conta, e.natureza.nome as natureza,e.valor as valor , e.descricao as descricao, e.id as id FROM EntidadeLancamento e", Tuple.class);
		List<Tuple> typles = query.getResultList();
		List lista = new ArrayList();
		typles.forEach(tuple -> {
			lista.add(vo(tuple));
		});
		return lista;
	}

	private Object vo(Tuple tuple) {
		try {
			Object instance = vo.newInstance();
			for(TupleElement e: tuple.getElements()) {
				Field field = vo.getDeclaredField(e.getAlias());
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
