package open.digytal.webapi.resource;

import java.util.List;

import open.digytal.util.Filtro;
//https://docs.swagger.io/spec.html
public class Search {
	List<String> f;
	public List<String> getF() {
		return f;
	}
	public void setF(List<String> f) {
		this.f = f;
	}
	public List<Filtro> getFiltros(){
		return null;
	}
	/*
	 * switch (input) {
        case ':':
            return EQUALITY;
        case '!':
            return NEGATION;
        case '>':
            return GREATER_THAN;
        case '<':
            return LESS_THAN;
        case '~':
            return LIKE;
        default:
            return null;
        }
	 * 
	 * 
	 */
}
