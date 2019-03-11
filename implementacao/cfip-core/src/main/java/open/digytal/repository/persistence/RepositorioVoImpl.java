package open.digytal.repository.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import open.digytal.util.Filtro;
@Repository
public class RepositorioVoImpl implements RepositorioVo {
	@PersistenceContext
	private EntityManager em;
	private Class vo;
	private String sql;
	private Filtro[] filtros;
	@Override
	public List listarVo(Class vo,String sql, List<Filtro> filtros) {
		return listarVo(vo, sql, filtros.stream().toArray(Filtro[]::new));
	}
	@Override
	public List listarVo(Class vo,String sql, Filtro ... filtros) {
		this.vo=vo;
		this.sql=sql;
		this.filtros=filtros;
		TypedQuery<Tuple> query = em.createQuery(getSql(), Tuple.class);
		if(filtros.length > 0) {
            for (Filtro filtro : filtros) {
                if(!filtro.isOrdem() && !filtro.isTodos())
                    query.setParameter(filtro.getParametro(), filtro.getValor());
            }
        }
		List<Tuple> typles = query.getResultList();
		List lista = new ArrayList();
		typles.forEach(tuple -> {
			lista.add(vo(tuple));
		});
		return lista;
	}
	public String getSql() {
        StringBuilder sql = new StringBuilder(this.sql);
        if(filtros.length > 0) {
            for (Filtro filtro : filtros) {
                if(!filtro.isOrdem() && !filtro.isTodos()) {
                    String append = String.format(" %s e.%s %s :%s", filtro.getJuncao(), filtro.getCampo(), filtro.getOperador(), filtro.getParametro());
                    sql.append(append);
                }else{
                    sql.append(" ORDER BY " + filtro.getCampo());
                }
            }
        }
        return sql.toString();
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
