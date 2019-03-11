package open.digytal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.entity.EntidadeConta;

public interface ContaRepository extends JpaRepository<EntidadeConta, Integer> {
	@Query("SELECT e FROM EntidadeConta e WHERE e.login= :login AND e.cartaoCredito=false ORDER BY e.nome")
	public List<EntidadeConta> listar(@Param("login")String login);
	
	@Query("SELECT e FROM EntidadeConta e WHERE e.id= :id ORDER BY e.nome")
	public List<EntidadeConta> listar(@Param("id")Integer id);
	
	@Query("SELECT e FROM EntidadeConta e WHERE e.login= :login AND e.nome like %:nome% ORDER BY e.nome")
	public List<EntidadeConta> listar(@Param("login")String login, @Param("nome")String nome);
	
	@Query("SELECT e FROM EntidadeConta e WHERE e.login= :login ORDER BY e.nome")
	public List<EntidadeConta> listarTodas(@Param("login")String login);
	
	@Query("SELECT e FROM EntidadeConta e WHERE e.login= :login AND e.cartaoCredito=true ORDER BY e.nome")
	public List<EntidadeConta> listarCartoesCredito(@Param("login")String login);

}

