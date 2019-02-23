package open.digytal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.model.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.LancamentoRepository;

@Controller
public class LancamentoController {
	@Autowired
	private ContaRepository contaRepository;
	@Autowired
	private LancamentoRepository repository;
	@Transactional
	public void incluir(Lancamento lancamento) {
		TipoMovimento tipoMovimento = lancamento.getTipoMovimento();
		Double valor = tipoMovimento==TipoMovimento.C? lancamento.getValor() : lancamento.getValor() * -1;
		Conta conta = lancamento.getConta();
		if (!lancamento.isPrevisao()) {
			conta.setSaldoAtual(conta.getSaldoAtual() + valor);
		}
		contaRepository.save(conta);
		repository.save(lancamento);
	}
}
