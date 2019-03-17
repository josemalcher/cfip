package open.digytal.controller;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import open.digytal.model.Lancamento;
import open.digytal.model.Lancamentos;
import open.digytal.model.Parcelas;
import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeLancamento;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.entity.EntidadeParcela;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.LancamentoRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.repository.ParcelaRepository;
import open.digytal.repository.persistence.Repositorio;
import open.digytal.service.LancamentoService;
import open.digytal.util.Calendario;
import open.digytal.util.Filtro;
import open.digytal.util.Filtros;

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

	@Autowired
	private Repositorio repositorio;			

	@PersistenceContext
	private EntityManager em;
	
	private final String SQL_EXTRATO_LANCAMENTO_PREVISAO = "SELECT e.id as id, e.data as data, e.tipoMovimento as tipoMovimento , e.conta.nome as conta, e.natureza.nome as natureza, "
															+ "e.descricao as descricao, e.valor as valor, e.parcelamento.restante as restante FROM EntidadeLancamento e ";
	
	private final String SQL_PARCELA_FATURA = "SELECT e.id as id, e.vencimento as vencimento, e.numero as numero, e.valor as valor, e.lancamento.id as lancamento, e.valor as amortizado, "
												+ " e.lancamento.conta.nome as conta, e.lancamento.natureza.nome as natureza, e.lancamento.tipoMovimento as tipoMovimento, CONCAT ('PARC: ',e.numero, ' - ', e.lancamento.descricao) as descricao "
												+ " FROM EntidadeParcela e";
	@Override
	public List<Parcelas> listarParcelas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarParcelasFaturas(false,login,inicio,fim,conta,natureza);
	}
	public List<Parcelas> listarFaturas(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarParcelasFaturas(true,login,inicio,fim,conta,natureza);
	}
	public List<Parcelas> listarParcelasFaturas(boolean cc,String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		List<Filtro> filtros = Filtros.igual("lancamento.conta.login", login).e().igual("compensada", false).e().igual("lancamento.conta.cartaoCredito", cc).e().igual("lancamento.natureza.id", natureza).e().igual("lancamento.conta.id", conta).lista();
		List<Parcelas> lista = repositorio.listar(Parcelas.class,SQL_PARCELA_FATURA,filtros);
		return lista;
	}

	@Override
	public List<Lancamentos> extrato(Integer contaId, Date dataInicio) {
		List<Filtro> filtros = Filtros.igual("previsao", false).e().igual("conta.id", contaId).e().maiorIgual("data", dataInicio).lista();
		List<Lancamentos> lista = repositorio.listar(Lancamentos.class,SQL_EXTRATO_LANCAMENTO_PREVISAO,filtros);
		return lista;
	}

	public List<Lancamentos> listarLancamentos(String login, Date inicio, Date fim, Integer conta,
			Integer natureza) {
		return listarLancamentos(false, login, inicio, fim, conta, natureza);
	}

	public List<Lancamentos> listarPrevisoes(String login, Date inicio, Date fim, Integer conta, Integer natureza) {
		return listarLancamentos(true, login, inicio, fim, conta, natureza);
	}
	private List<Lancamentos> listarLancamentos(boolean previsao, String login, Date inicio, Date fim,Integer conta, Integer natureza){
		List<Filtro> filtros = Filtros.igual("previsao", previsao).e().igual("conta.login", login).e().igual("conta.id", conta).e().igual("natureza.id", natureza).e().maiorIgual("data", inicio).e().menorIgual("data", fim).lista();
		List<Lancamentos> lista = repositorio.listar(Lancamentos.class,SQL_EXTRATO_LANCAMENTO_PREVISAO,filtros);
		return lista;
	}
	/*
	 * private List<EntidadeLancamento> listarLancamentos(boolean previsao, String
	 * login, Date inicio, Date fim,Integer conta, Integer natureza) { StringBuilder
	 * sql = new StringBuilder(SQL_LANCAMENTO_PREVISAO); if (natureza != null &&
	 * natureza > 0) { sql.append(" AND l.natureza.id=:natureza "); } if (conta !=
	 * null && conta > 0) { sql.append(" AND l.conta.id=:conta "); } sql =
	 * sql.append(" ORDER BY l.data");
	 * 
	 * TypedQuery<EntidadeLancamento> query = em.createQuery(sql.toString(),
	 * EntidadeLancamento.class); query.setParameter("inicio", inicio);
	 * query.setParameter("fim", fim); query.setParameter("previsao", previsao);
	 * query.setParameter("login", login); if (natureza != null && natureza > 0)
	 * query.setParameter("natureza", natureza);
	 * 
	 * if (conta != null && conta > 0) query.setParameter("conta", conta);
	 * 
	 * List<EntidadeLancamento> lista = query.getResultList(); if (previsao) return
	 * lista; else { List<EntidadeLancamento> lancamentos = lista.stream().filter(l
	 * -> !l.getConta().isCartaoCredito()).collect(Collectors.toList()); return
	 * lancamentos; } }
	 */
	

	public void incluir(Lancamento objeto) { 
		EntidadeLancamento entidade = new EntidadeLancamento(); 
		BeanUtils.copyProperties(objeto, entidade);
		BeanUtils.copyProperties(objeto.getParcelamento(),entidade.getParcelamento());
		entidade.setConta(contaRepository.findById(objeto.getConta()).get());
		if(objeto.getDestino()!=null && objeto.getDestino()>0)
			entidade.setDestino(contaRepository.findById(objeto.getDestino()).get());
		EntidadeNatureza natureza=naturezaRepository.findById(objeto.getNatureza()).get();
		entidade.setNatureza(natureza);
		entidade.setTipoMovimento(natureza.getTipoMovimento());
		incluir(entidade);	
	 }

	@Transactional
	public EntidadeLancamento incluir(EntidadeLancamento lancamento) {
		lancamento.configurar();
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
		if(lancamento.getNatureza().getTipoMovimento().isTranferencia()) {
			EntidadeLancamento transferencia=lancamento.transferencia();
			transferencia.configurar();
			repository.save(transferencia);
			EntidadeConta destino = transferencia.getConta();
			destino.atualizarSaldo(transferencia);
			contaRepository.save(destino);
			
		}
		
		
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

	@Override
	@Transactional
	public void compensarParcela(Date data, Parcelas... parcelas) {
		for (Parcelas p : parcelas) {
			Double valor = p.getAmortizado();
			EntidadeParcela parcela =em.find(EntidadeParcela.class, p.getId());
			if (valor == null)
				valor = parcela.getValor();
			if (valor < 0)
				valor = valor * -1;
			valor = parcela.getLancamento().getTipoMovimento() == TipoMovimento.C ? valor : valor * -1;

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
				compensacao.configurar();
				repository.save(compensacao);
			}
			lancamento.getParcelamento().setRestante(lancamento.getParcelamento().getRestante() - valor);
			repository.save(lancamento);
		}

	}
	

}
