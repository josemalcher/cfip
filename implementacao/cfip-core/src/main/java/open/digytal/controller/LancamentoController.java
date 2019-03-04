package open.digytal.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	
	//https://www.baeldung.com/spring-data-jpa-query

	private final String SQL_LANCAMENTO_PREVISAO = "SELECT l FROM Lancamento l WHERE (l.conta.cartaoCredito=true OR l.previsao = :previsao) AND l.data BETWEEN :inicio AND :fim ";
	private final String SQL_PARCELA_FATURA = "SELECT p FROM Parcela p WHERE p.lancamento.conta.cartaoCredito =:cc AND p.compensada =false AND p.vencimento BETWEEN :inicio AND :fim ";
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
		if(previsao)
			return lista;
		else {
			List<Lancamento> lancamentos = lista.stream()
					  .filter(l -> !l.getConta().isCartaoCredito())
					  .collect(Collectors.toList());
			return lancamentos;
		}
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
		return listarParcelas(false, inicio, fim, conta, natureza);
	}
	public List<Parcela> listarFaturas(Date inicio, Date fim, Integer conta,Integer natureza) {
		return listarParcelas(true, inicio, fim, conta, natureza);
	}
	private List<Parcela> listarParcelas(boolean cc,Date inicio, Date fim, Integer conta,Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_PARCELA_FATURA);

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
		query.setParameter("cc", cc);
		if (natureza != null && natureza > 0)
			query.setParameter("natureza", natureza);

		if (conta != null && conta > 0)
			query.setParameter("conta", conta);

		List<Parcela> lista = query.getResultList();
		lista.forEach(item->item.setAmortizado(item.getValor()));
		return lista;
	}
	@Transactional
	public void incluir(Lancamento lancamento) {
		if(lancamento.getNatureza().getTipoMovimento().isTranferencia()) {
			Lancamento transferencia=lancamento.transferencia();
			repository.save(transferencia);
			Conta destino = transferencia.getConta();
			destino.atualizarSaldo(transferencia);
			contaRepository.save(destino);
			
		}
		Conta conta = lancamento.getConta();
		conta.atualizarSaldo(lancamento);
		contaRepository.save(conta);
		int resto=0;
		if(lancamento.isPrevisao() || lancamento.getConta().isCartaoCredito()) {
			Double valor = lancamento.getValor();
			if (lancamento.getParcelamento().isRateio()) {
				valor = new Double(new Double( lancamento.getValor() / lancamento.getParcelamento().getNumeroParcelas()).intValue());
				resto=(int) (lancamento.getValor() % lancamento.getParcelamento().getNumeroParcelas());
			}
			else {
				lancamento.setValor(valor * lancamento.getParcelamento().getNumeroParcelas());
			}
			lancamento= gerarParcelas(lancamento, valor,resto);
		}
		repository.save(lancamento);
	}
	
	private Lancamento gerarParcelas(Lancamento lancamento, Double valorParcela, int resto){
		if(lancamento.getConta().isCartaoCredito())
			lancamento.setPrevisao(false);
		Date vencimento = lancamento.getParcelamento().getPrimeiroVencimento();
		for (int numero = lancamento.getParcelamento().getPrimeiraParcela(); numero <= lancamento.getParcelamento().getUltimaParcela(); numero++) {
			Parcela parcela = new Parcela();
			parcela.setLancamento(lancamento);
			parcela.setNumero(numero);
			parcela.setVencimento(vencimento);
			parcela.setValor(valorParcela+(numero==1?resto:0));
			vencimento = Calendario.rolarMes(vencimento, 1);
			lancamento.getParcelamento().addParcela(parcela);
		}
		return lancamento;
	}
	@Transactional
	public void compensarParcela(Date data, Parcela ... parcelas) {
		for(Parcela parcela: parcelas) {
			Double valor=parcela.getAmortizado();
			if(valor==null)
				valor=parcela.getValor();
			if(valor<0)
				valor = valor *-1;
			valor=parcela.getLancamento().getTipoMovimento()==TipoMovimento.C?valor:valor * -1;
			
			Lancamento lancamento = parcela.getLancamento();

			parcela.setValor(parcela.getValor() - valor);
			parcela.setCompensacao(parcela.isCompensada()?data:null);
			parcela.setCompensada(parcela.getValor().equals(0.0d));
			parcelaRepository.save(parcela);
			if(!parcela.getLancamento().getConta().isCartaoCredito()) {
				Conta conta = lancamento.getConta();
				conta.setSaldoAtual(conta.getSaldoAtual() + valor);
				contaRepository.save(conta);
				Lancamento compensacao = lancamento.compensacao(valor,parcela.getNumero());
				repository.save(compensacao);
			}
			lancamento.getParcelamento().setRestante(lancamento.getParcelamento().getRestante() - valor);
			repository.save(lancamento);
		}
		
		
	}
	
}
