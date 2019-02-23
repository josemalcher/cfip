package open.digytal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Integer> {
	List<Conta> listar();
	List<Conta> listar(String nome);
}
