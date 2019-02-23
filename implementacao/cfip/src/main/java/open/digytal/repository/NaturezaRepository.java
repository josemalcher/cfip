package open.digytal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.Conta;
import open.digytal.model.Natureza;

public interface NaturezaRepository extends JpaRepository<Natureza, Integer> {
	List<Natureza> listar();
	List<Natureza> listar(String nome);
}
