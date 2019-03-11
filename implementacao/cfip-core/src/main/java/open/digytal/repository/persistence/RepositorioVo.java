package open.digytal.repository.persistence;

import java.util.List;

import open.digytal.util.Filtro;

public interface RepositorioVo {
	void setClasse(Class classe);
	void setSql(String sql);
	List listar(Filtro ... filtros);
	List listar(List<Filtro> filtros);
}
