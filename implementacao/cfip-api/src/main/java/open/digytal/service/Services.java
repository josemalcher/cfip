package open.digytal.service;

import java.util.List;

import open.digytal.util.Filtro;

public interface Services <T> {
	<T> List<T> listar( Filtro... filtros) ;
}
