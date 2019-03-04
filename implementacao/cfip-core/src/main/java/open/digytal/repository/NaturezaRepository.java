package open.digytal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;

public interface NaturezaRepository extends JpaRepository<Natureza, Integer> {
	@Query("SELECT e FROM Natureza e ORDER BY e.nome")
	List<Natureza> listar();
	@Query("SELECT e FROM Natureza e WHERE e.nome = :nome ORDER BY e.nome")
	List<Natureza> listar(@Param("nome")String nome);
	
	@Query("SELECT e FROM Natureza e WHERE e.tipoMovimento = :tipo ORDER BY e.nome")
	List<Natureza> listar(@Param("tipo")TipoMovimento tipo);
}
