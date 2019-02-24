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
		boolean transfere=TipoMovimento.T == tipoMovimento;
		Double valor = tipoMovimento==TipoMovimento.C? lancamento.getValor() : lancamento.getValor() * -1;
		
		lancamento.setValor(valor);
		lancamento.setTransferencia(transfere);
		lancamento= repository.save(lancamento);
		
		if (transfere) {
			lancamento.setTipoMovimento(TipoMovimento.D);
			Lancamento transferencia = lancamento.copia();
			transferencia.setValor(valor *-1);
			repository.save(transferencia);
		}
		
		if (!lancamento.isPrevisao()) {
			Conta conta = lancamento.getConta();
			conta.setSaldoAtual(conta.getSaldoAtual() + valor);
			contaRepository.save(conta);
			if (transfere) {
				Conta destino = lancamento.getDestino();
				destino.setSaldoAtual(destino.getSaldoAtual() + (valor *-1));
				contaRepository.save(destino);
			}
		}
		
	}
}
