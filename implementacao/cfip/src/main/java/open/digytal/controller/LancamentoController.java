package open.digytal.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.Parcela;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.LancamentoRepository;
import open.digytal.util.Calendario;

@Controller
public class LancamentoController {
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private LancamentoRepository repository;

	@PersistenceContext
	private EntityManager em;

	private final String SQL_LANCAMENTO_PREVISAO = "SELECT l FROM Lancamento l WHERE l.previsao = :previsao AND l.data BETWEEN :inicio AND :fim ";

	private List<Lancamento> listarLancamentos(boolean previsao, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO);

		if (natureza != null && natureza > 0) {
			sql.append(" AND l.natureza.id=:natureza ");
		}
		if (conta != null && conta > 0) {
			sql.append(" AND l.conta.id=:conta ");
		}
		sql = sql.append(" ORDER BY l.data");

		TypedQuery<Lancamento> query = em.createQuery(sql.toString(), Lancamento.class);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		query.setParameter("previsao", previsao);
		if (natureza != null && natureza > 0)
			query.setParameter("natureza", natureza);

		if (conta != null && conta > 0)
			query.setParameter("conta", conta);

		List<Lancamento> lista = query.getResultList();
		// ISSO É PARA TRAZER SÓ AS TRANSFERENCIAS DE CREDITO NA PREVISAO
		List<Lancamento> minLista = new ArrayList<Lancamento>();
		for (Lancamento l : lista) {
			if (l.getTipoMovimento() == TipoMovimento.C
					|| (l.getTipoMovimento() == TipoMovimento.D && !(l.isTransferencia()))) {
				minLista.add(l);
			}
		}
		/*
		 * if (previsao) return minLista; else
		 */
		return lista;
	}

	// TELA DE LANCAMENTOS
	public List<Lancamento> listarLancamentos(Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(false, inicio, fim, conta, natureza);
	}
	// TELA DE PREVISOES
	public List<Lancamento> listarPrevisoes(Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(true, inicio, fim, conta, natureza);
	}
	@Transactional
	public void incluir(Lancamento lancamento) {
		boolean previsao = lancamento.isPrevisao();
		if (previsao) {
			Date vencimento = lancamento.getParcelamento().getVencimento();
			String[] intervalo = lancamento.getParcelamento().getConfiguracao().split("-");
			Integer inicio = Integer.valueOf(intervalo[0].trim());
			Integer fim = Integer.valueOf(intervalo[1].trim());
			Integer parcelas = 1 + (fim - inicio);
			Double valor = lancamento.getValor();
			if (lancamento.getParcelamento().isRateio())
				valor = lancamento.getValor() / parcelas;
			else {
				lancamento.setValor(valor * parcelas);
				lancamento.getParcelamento().setRestante(lancamento.getValor());
				;
			}
			for (int numero = inicio; numero <= fim; numero++) {
				Parcela parcela = new Parcela();
				parcela.setLancamento(lancamento);
				parcela.setNumero(numero);
				parcela.setVencimento(vencimento);
				parcela.setValor(valor);
				vencimento = Calendario.rolarMes(vencimento, 1);
				lancamento.getParcelamento().addParcela(parcela);
			}
		} else {
			if (lancamento.getTipoMovimento() == TipoMovimento.T) {
				// A magia está aqui
				Lancamento transferencia = lancamento.copia();
				repository.save(transferencia);
				Conta destino = transferencia.getConta();
				destino.setSaldoAtual(destino.getSaldoAtual() + transferencia.getValor());
				contaRepository.save(destino);
			}
			Conta conta = lancamento.getConta();
			conta.setSaldoAtual(conta.getSaldoAtual() + lancamento.getValorMovimento());
			contaRepository.save(conta);
		}
		repository.save(lancamento);
	}
}
