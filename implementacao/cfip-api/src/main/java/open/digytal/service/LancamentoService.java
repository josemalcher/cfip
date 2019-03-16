package open.digytal.service;

import java.util.Date;
import java.util.List;

import open.digytal.model.Lancamento;
import open.digytal.model.Parcelas;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeParcela;

public interface LancamentoService {
	void incluir(Lancamento entidade);

	List<EntidadeLancamento> extrato(Integer contaId, Date dataInicio);

	List<EntidadeLancamento> listarLancamentos(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<EntidadeLancamento> listarPrevisoes(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<Parcelas> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<Parcelas> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	void compensarParcela(Date data, Parcelas... parcelas);

	EntidadeParcela buscarParcela(Integer id);
}
