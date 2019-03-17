package open.digytal.service;

import java.util.Date;
import java.util.List;

import open.digytal.model.Lancamento;
import open.digytal.model.Lancamentos;
import open.digytal.model.Parcelas;

public interface LancamentoService {
	void incluir(Lancamento entidade);

	List<Lancamentos> extrato(Integer contaId, Date dataInicio);

	List<Lancamentos> listarLancamentos(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<Lancamentos> listarPrevisoes(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<Parcelas> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	List<Parcelas> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza);

	void compensarParcela(Date data, Parcelas... parcelas);

	//EntidadeParcela buscarParcela(Integer id);
}
