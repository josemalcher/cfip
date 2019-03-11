package open.digytal.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;import org.hibernate.engine.jdbc.env.spi.NameQualifierSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import open.digytal.model.Lancamento;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.LancamentoRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.repository.ParcelaRepository;
import open.digytal.service.LancamentoService;
import open.digytal.util.Calendario;

@Controller
public class LancamentoController implements LancamentoService {
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private NaturezaRepository naturezaRepository;
	@Autowired
	private ParcelaRepository parcelaRepository;
	@Autowired
	private LancamentoRepository repository;

	@PersistenceContext
	private EntityManager em;

	// https://www.baeldung.com/spring-data-jpa-query

	private final String SQL_LANCAMENTO_PREVISAO = "SELECT l FROM EntidadeLancamento l WHERE l.conta.login=:login AND  (l.conta.cartaoCredito=true OR l.previsao = :previsao) AND l.data BETWEEN :inicio AND :fim ";
	private final String SQL_PARCELA_FATURA = "SELECT p FROM EntidadeParcela p WHERE p.lancamento.conta.login=:login AND p.lancamento.conta.cartaoCredito =:cc AND p.compensada =false AND p.vencimento BETWEEN :inicio AND :fim ";

	public List<EntidadeLancamento> extrato(Integer contaId, Date dataInicio) {
		return repository.extrato(contaId, dataInicio);
	}

	private List<EntidadeLancamento> listarLancamentos(boolean previsao, String login, Date inicio, Date fim,
			Integer conta, Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO);
		if (natureza != null && natureza > 0) {
			sql.append(" AND l.natureza.id=:natureza ");
		}
		if (conta != null && conta > 0) {
			sql.append(" AND l.conta.id=:conta ");
		}
		sql = sql.append(" ORDER BY l.data");

		TypedQuery<EntidadeLancamento> query = em.createQuery(sql.toString(), EntidadeLancamento.class);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		query.setParameter("previsao", previsao);
		query.setParameter("login", login);
		if (natureza != null && natureza > 0)
			query.setParameter("natureza", natureza);

		if (conta != null && conta > 0)
			query.setParameter("conta", conta);

		List<EntidadeLancamento> lista = query.getResultList();
		if (previsao)
			return lista;
		else {
			List<EntidadeLancamento> lancamentos = lista.stream().filter(l -> !l.getConta().isCartaoCredito())
					.collect(Collectors.toList());
			return lancamentos;
		}
	}

	public EntidadeParcela buscarParcela(Integer id) {
		return em.find(EntidadeParcela.class, id);
	}

	public List<EntidadeLancamento> listarLancamentos(String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		return listarLancamentos(false, login, inicio, fim, conta, natureza);
	}

	public List<EntidadeLancamento> listarPrevisoes(String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		return listarLancamentos(true, login, inicio, fim, conta, natureza);
	}

	public List<EntidadeParcela> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarParcelas(false, login, inicio, fim, conta, natureza);
	}

	public List<EntidadeParcela> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarParcelas(true, login, inicio, fim, conta, natureza);
	}

	private List<EntidadeParcela> listarParcelas(boolean cc, String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		StringBuilder sql = new StringBuilder(SQL_PARCELA_FATURA);

		if (natureza != null && natureza > 0) {
			sql.append(" AND p.lancamento.natureza.id=:natureza ");
		}
		if (conta != null && conta > 0) {
			sql.append(" AND p.lancamento.conta.id=:conta ");
		}
		sql = sql.append(" ORDER BY p.vencimento");

		TypedQuery<EntidadeParcela> query = em.createQuery(sql.toString(), EntidadeParcela.class);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		query.setParameter("cc", cc);
		query.setParameter("login", login);
		if (natureza != null && natureza > 0)
			query.setParameter("natureza", natureza);

		if (conta != null && conta > 0)
			query.setParameter("conta", conta);

		List<EntidadeParcela> lista = query.getResultList();
		lista.forEach(item -> item.setAmortizado(item.getValor()));
		return lista;
	}

	public void incluir(Lancamento objeto) { 
		EntidadeLancamento entidade = new EntidadeLancamento(); 
		BeanUtils.copyProperties(objeto, entidade);
		BeanUtils.copyProperties(objeto.getParcelamento(),entidade.getParcelamento());
		entidade.setConta(contaRepository.findById(objeto.getConta()).get());
		EntidadeNatureza natureza=naturezaRepository.findById(objeto.getNatureza()).get();
		entidade.setNatureza(natureza);
		entidade.setTipoMovimento(natureza.getTipoMovimento());
		incluir(entidade);	
	 }

	@Transactional
	public EntidadeLancamento incluir(EntidadeLancamento lancamento) {
		if(lancamento.getNatureza().getTipoMovimento().isTranferencia()) {
			EntidadeLancamento transferencia=lancamento.transferencia();
			repository.save(transferencia);
			EntidadeConta destino = transferencia.getConta();
			destino.atualizarSaldo(transferencia);
			contaRepository.save(destino);
			
		}
		EntidadeConta conta = lancamento.getConta();
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
		return lancamento;
	}

	private EntidadeLancamento gerarParcelas(EntidadeLancamento lancamento, Double valorParcela, int resto) {
		if (lancamento.getConta().isCartaoCredito())
			lancamento.setPrevisao(false);
		Date vencimento = lancamento.getParcelamento().getPrimeiroVencimento();
		for (int numero = lancamento.getParcelamento().getPrimeiraParcela(); numero <= lancamento.getParcelamento()
				.getUltimaParcela(); numero++) {
			EntidadeParcela parcela = new EntidadeParcela();
			parcela.setLancamento(lancamento);
			parcela.setNumero(numero);
			parcela.setVencimento(vencimento);
			parcela.setValor(valorParcela + (numero == 1 ? resto : 0));
			vencimento = Calendario.rolarMes(vencimento, 1);
			lancamento.getParcelamento().addParcela(parcela);
		}
		return lancamento;
	}

	@Transactional
	public void compensarParcela(Date data, EntidadeParcela... parcelas) {
		for (EntidadeParcela parcela : parcelas) {
			Double valor = parcela.getAmortizado();
			if (valor == null)
				valor = parcela.getValor();
			if (valor < 0)
				valor = valor * -1;
			valor = parcela.getLancamento().getTipoMovimento() == TipoMovimento.C ? valor : valor - 1;

			EntidadeLancamento lancamento = parcela.getLancamento();

			parcela.setValor(parcela.getValor() - valor);
			parcela.setCompensacao(parcela.isCompensada() ? data : null);
			parcela.setCompensada(parcela.getValor().equals(0.0d));
			parcelaRepository.save(parcela);
			if (!parcela.getLancamento().getConta().isCartaoCredito()) {
				EntidadeConta conta = lancamento.getConta();
				conta.setSaldoAtual(conta.getSaldoAtual() + valor);
				contaRepository.save(conta);
				EntidadeLancamento compensacao = lancamento.compensacao(valor, parcela.getNumero());
				repository.save(compensacao);
			}
			lancamento.getParcelamento().setRestante(lancamento.getParcelamento().getRestante() - valor);
			repository.save(lancamento);
		}

	}

}
