package open.digytal.webapi.resource;

import java.util.List;

import open.digytal.util.Filtro;

public class Search {
	List<Filtro> filtros;
	public List<Filtro> getFiltros() {
		return filtros;
	}
	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}
}
