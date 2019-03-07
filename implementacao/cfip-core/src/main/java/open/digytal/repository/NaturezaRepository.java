package open.digytal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.Natureza;
import open.digytal.model.TipoMovimento;

public interface NaturezaRepository extends JpaRepository<Natureza, Integer> {
	@Query("SELECT e FROM Natureza WHERE e.login= :login e ORDER BY e.nome")
	List<Natureza> listarTodas(@Param("login")String login);
	
	@Query("SELECT e FROM Natureza e WHERE e.login= :login AND e.nome = :nome ORDER BY e.nome")
	List<Natureza> listar(@Param("login")String login,@Param("nome")String nome);
	
	@Query("SELECT e FROM Natureza e WHERE e.login= :login AND e.tipoMovimento = :tipo ORDER BY e.nome")
	List<Natureza> listar(@Param("login")String login,@Param("tipo")TipoMovimento tipo);
}
