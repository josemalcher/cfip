package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.entity.EntidadeParcela;

public interface ParcelaRepository extends JpaRepository<EntidadeParcela, Integer> {
	
}

