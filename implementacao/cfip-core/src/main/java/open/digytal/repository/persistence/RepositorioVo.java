package open.digytal.repository.persistence;

import java.util.List;
import java.util.Map;

public interface RepositorioVo {
	List listar(Class vo,String sql, Map<String,Object> params);
}
