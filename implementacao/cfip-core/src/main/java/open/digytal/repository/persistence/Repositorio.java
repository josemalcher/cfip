package open.digytal.repository.persistence;

import java.util.List;

import javax.persistence.EntityManager;

import open.digytal.util.Filtro;

public interface Repositorio {
	List listar(Class classe, String sql,Filtro ... filtros);
	List listar(Class classe, Filtro ... filtros);
	List listar(Class classe, String sql,List<Filtro> filtros);
	List listar(Class classe, List<Filtro> filtros);
	EntityManager getEm();
}
