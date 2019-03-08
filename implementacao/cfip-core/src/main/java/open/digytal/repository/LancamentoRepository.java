package open.digytal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import open.digytal.model.EntidadeLancamento;

public interface LancamentoRepository extends JpaRepository<EntidadeLancamento, Integer> {

}
