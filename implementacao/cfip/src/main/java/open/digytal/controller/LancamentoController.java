package open.digytal.controller;

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
import open.digytal.repository.ParcelaRepository;
import open.digytal.util.Calendario;

@Controller
public class LancamentoController {
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private ParcelaRepository parcelaRepository;
	@Autowired
	private LancamentoRepository repository;

	@PersistenceContext
	private EntityManager em;

	private final String SQL_LANCAMENTO_PREVISAO = "SELECT l FROM Lancamento l WHERE l.previsao = :previsao AND l.data BETWEEN :inicio AND :fim ";
	private final String SQL_PARCELA = "SELECT p FROM Parcela p WHERE p.compensada =false AND p.vencimento BETWEEN :inicio AND :fim ";
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
		return lista;
	}
	public Parcela buscarParcela(Integer id) {
		return em.find(Parcela.class, id);
	}
	public List<Lancamento> listarLancamentos(Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(false, inicio, fim, conta, natureza);
	}
	public List<Lancamento> listarPrevisoes(Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(true, inicio, fim, conta, natureza);
	}
	public List<Parcela> listarParcelas(Date inicio, Date fim, Integer conta,Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_PARCELA);

		if (natureza != null && natureza > 0) {
			sql.append(" AND p.lancamento.natureza.id=:natureza ");
		}
		if (conta != null && conta > 0) {
			sql.append(" AND p.lancamento.conta.id=:conta ");
		}
		sql = sql.append(" ORDER BY p.vencimento");

		TypedQuery<Parcela> query = em.createQuery(sql.toString(), Parcela.class);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		if (natureza != null && natureza > 0)
			query.setParameter("natureza", natureza);

		if (conta != null && conta > 0)
			query.setParameter("conta", conta);

		List<Parcela> lista = query.getResultList();
		return lista;
	}
	@Transactional
	public void incluir(Lancamento lancamento) {
		boolean previsao = lancamento.isPrevisao();
		if (previsao) {
			Date primeiroVencimento = lancamento.getParcelamento().getPrimeiroVencimento();
			Integer primeiraParcela = lancamento.getParcelamento().getPrimeiraParcela();
			Integer ultimaParcela = lancamento.getParcelamento().getUltimaParcela();
			Integer parcelas = 1 + (ultimaParcela - primeiraParcela);
			Double valor = lancamento.getValorMovimento();
			if (lancamento.getParcelamento().isRateio())
				valor = lancamento.getValor() / parcelas;
			else {
				lancamento.setValor(valor * parcelas);
			}
			gerarParcelas(primeiroVencimento, valor, primeiraParcela, ultimaParcela, lancamento);
			if(lancamento.getConta().isCartaoCredito()) {
				Conta conta = lancamento.getConta();
				conta.setSaldoAtual(conta.getSaldoAtual() + lancamento.getValor());
				contaRepository.save(conta);
			}
		} else {
			if (lancamento.getTipoMovimento() == TipoMovimento.T) {
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
	
	private void gerarParcelas(Date vencimento,Double valor,Integer primeira, Integer ultima,Lancamento lancamento){
		for (int numero = primeira; numero <= ultima; numero++) {
			Parcela parcela = new Parcela();
			parcela.setLancamento(lancamento);
			parcela.setNumero(numero);
			parcela.setVencimento(vencimento);
			parcela.setValor(valor);
			vencimento = Calendario.rolarMes(vencimento, 1);
			lancamento.getParcelamento().addParcela(parcela);
		}
	}
	@Transactional
	public void compensarParcela(Parcela parcela, Date data) {
		parcela.setCompensada(true);
		parcela.setCompensacao(data);
		Lancamento novoLancamento = Lancamento.compensacao(parcela);
		Conta conta = novoLancamento.getConta();
		conta.setSaldoAtual(conta.getSaldoAtual() + parcela.getValor());

		Lancamento lancamento = parcela.getLancamento();
		lancamento.getParcelamento().setRestante(lancamento.getParcelamento().getRestante() - parcela.getValor());

		contaRepository.save(conta);
		repository.save(novoLancamento);
		repository.save(lancamento);
		parcelaRepository.save(parcela);
	}
	
}
