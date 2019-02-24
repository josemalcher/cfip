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
		boolean previsao = lancamento.isPrevisao();
		if(previsao) {
			
		}else {
			if (lancamento.getTipoMovimento()==TipoMovimento.T) {
				//A magia está aqui
				Lancamento transferencia=lancamento.copia();
				repository.save(transferencia);
				Conta destino = transferencia.getConta();
				destino.setSaldoAtual(destino.getSaldoAtual() + transferencia.getValor());
				contaRepository.save(destino);
			}
			repository.save(lancamento);
			Conta conta = lancamento.getConta();
			conta.setSaldoAtual(conta.getSaldoAtual() + lancamento.getValor());
			contaRepository.save(conta);
		}
	}
}
