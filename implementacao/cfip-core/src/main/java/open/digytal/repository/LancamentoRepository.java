package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.entity.EntidadeLancamento;

public interface LancamentoRepository extends JpaRepository<EntidadeLancamento, Integer>{
	/*
	 * @Query("SELECT e FROM EntidadeLancamento e WHERE e.previsao=false AND e.conta.id= :id AND e.data>= :data ORDER BY e.data"
	 * ) public List<EntidadeLancamento> extrato(@Param("id") Integer
	 * id, @Param("data")Date dataInicio);
	 */
}
