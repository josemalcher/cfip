package open.digytal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.Lancamentos;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.repository.persistence.RepositorioVo;

public interface LancamentoRepository extends JpaRepository<EntidadeLancamento, Integer>, RepositorioVo<Lancamentos>{
	@Query("SELECT e FROM EntidadeLancamento e WHERE e.previsao=false AND e.conta.id= :id AND e.data>= :data ORDER BY e.data")
	public List<EntidadeLancamento> extrato(@Param("id") Integer id, @Param("data")Date dataInicio);
}
