package open.digytal.repository.persistence;

import java.util.List;

import open.digytal.util.Filtro;

public interface RepositorioVo {
	List listarVo(Class vo,String sql, Filtro ... filtros);
	List listarVo(Class vo,String sql, List<Filtro> filtros);
}
