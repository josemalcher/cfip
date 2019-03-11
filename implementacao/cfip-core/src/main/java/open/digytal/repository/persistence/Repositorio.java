package open.digytal.repository.persistence;

import java.util.List;

public interface Repositorio {
	List listar(Class classe, String sql);
}
