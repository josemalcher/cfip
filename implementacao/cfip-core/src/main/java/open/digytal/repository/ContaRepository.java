package open.digytal.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
	@Query("SELECT e FROM Conta e WHERE e.login= :login AND e.cartaoCredito=false ORDER BY e.nome")
	public List<Conta> listarContas(@Param("login")String login);
	
	@Query("SELECT e FROM Conta e WHERE e.login= :login ORDER BY e.nome")
	public List<Conta> listarTodas(@Param("login")String login);
	
	@Query("SELECT e FROM Conta e WHERE e.login= :login AND e.cartaoCredito=true ORDER BY e.nome")
	public List<Conta> listarCartoesCredito(@Param("login")String login);
	
	@Query("SELECT e FROM Conta e WHERE e.id= :id ORDER BY e.nome")
	public List<Conta> listar(@Param("id")Integer id);
	
	@Query("SELECT e FROM Conta e WHERE e.login= :login AND e.nome like %:nome% ORDER BY e.nome")
	public List<Conta> listar(@Param("login")String login, @Param("nome")String nome);
	
	@Query("SELECT e FROM Lancamento e WHERE e.previsao=false AND e.conta.id= :id AND e.data>= :data ORDER BY e.data")
	public List<Lancamento> extrato(@Param("id") Integer id, @Param("data")Date dataInicio);
	
}

