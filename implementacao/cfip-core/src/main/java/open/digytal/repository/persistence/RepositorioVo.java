package open.digytal.repository.persistence;

import java.util.List;

import open.digytal.util.Filtro;

public interface RepositorioVo {
	void setClasse(Class classe);
	void setSql(String sql);
	List listarVo(Filtro ... filtros);
	List listarVo(List<Filtro> filtros);
}
