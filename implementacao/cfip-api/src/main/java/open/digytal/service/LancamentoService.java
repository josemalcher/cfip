package open.digytal.service;

import java.util.Date;
import java.util.List;

import open.digytal.model.entity.EntidadeLancamento;

public interface LancamentoService {
	 List<EntidadeLancamento> extrato(Integer contaId, Date dataInicio);
}
