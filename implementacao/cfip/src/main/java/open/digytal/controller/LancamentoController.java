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
		Conta conta = lancamento.getConta();
		conta.atualizarSaldo(lancamento);
		contaRepository.save(conta);
		if(lancamento.isPrevisao() || lancamento.getConta().isCartaoCredito()) {
			Double valor = lancamento.getValor();
			if (lancamento.getParcelamento().isRateio())
				valor = lancamento.getValor() / lancamento.getParcelamento().getNumeroParcelas();
			else {
				lancamento.setValor(valor * lancamento.getParcelamento().getNumeroParcelas());
			}
			lancamento= gerarParcelas(lancamento, valor);
		}
		repository.save(lancamento);
	}
	
	private Lancamento gerarParcelas(Lancamento lancamento, Double valorParcela){
		Date vencimento = lancamento.getParcelamento().getPrimeiroVencimento();
		for (int numero = lancamento.getParcelamento().getPrimeiraParcela(); numero <= lancamento.getParcelamento().getUltimaParcela(); numero++) {
			Parcela parcela = new Parcela();
			parcela.setLancamento(lancamento);
			parcela.setNumero(numero);
			parcela.setVencimento(vencimento);
			parcela.setValor(valorParcela);
			vencimento = Calendario.rolarMes(vencimento, 1);
			lancamento.getParcelamento().addParcela(parcela);
		}
		return lancamento;
	}
	@Transactional
	public void compensarParcela(Parcela parcela, Date data) {
		parcela.setCompensada(true);
		parcela.setCompensacao(data);
		
		if(!parcela.getLancamento().getConta().isCartaoCredito()) {
			Lancamento novoLancamento = Lancamento.compensacao(parcela);
			repository.save(novoLancamento);
			
			Conta conta = novoLancamento.getConta();
			conta.setSaldoAtual(conta.getSaldoAtual() + parcela.getValor());
			contaRepository.save(conta);
		}
		
		Lancamento lancamento = parcela.getLancamento();
		lancamento.getParcelamento().setRestante(lancamento.getParcelamento().getRestante() + parcela.getValor());

		repository.save(lancamento);
		parcelaRepository.save(parcela);
	}
	
}
