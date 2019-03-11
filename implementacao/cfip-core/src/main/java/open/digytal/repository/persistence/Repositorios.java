package open.digytal.repository.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Repositorios implements Repositorio {
	@PersistenceContext
	protected EntityManager em;
	public List listar(Class classe, String sql) {
		System.out.println(em);
		System.out.println(sql);
		System.out.println(classe);
		return null;
	}
}
