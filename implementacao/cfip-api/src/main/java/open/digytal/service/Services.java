package open.digytal.service;

import java.util.List;

import open.digytal.util.Filtro;

public interface Services <T> {
	//List<T> listar( List<Filtro> filtros) ;
	List<T> listar( Filtro... filtros) ;
}
