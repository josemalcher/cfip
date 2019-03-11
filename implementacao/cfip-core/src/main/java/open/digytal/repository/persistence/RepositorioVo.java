package open.digytal.repository.persistence;

import java.util.List;

public interface RepositorioVo<T> {
	List<T> listar();
}
