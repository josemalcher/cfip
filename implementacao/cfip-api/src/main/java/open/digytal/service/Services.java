package open.digytal.service;

import java.util.List;

import open.digytal.util.Filtro;

public interface Services <T> {
	List<T> listar( Filtro... filtros) ;
	<T> T buscar(Object id);
	//<T> T incluir(T entidade);
}
