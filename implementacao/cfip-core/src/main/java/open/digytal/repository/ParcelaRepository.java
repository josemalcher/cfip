package open.digytal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.entity.EntidadeParcela;

public interface ParcelaRepository extends JpaRepository<EntidadeParcela, Integer> {
	//@EntityGraph(value = "parcela.lancamento", type = EntityGraphType.LOAD)
	Optional<EntidadeParcela> findById(Integer id);
}

