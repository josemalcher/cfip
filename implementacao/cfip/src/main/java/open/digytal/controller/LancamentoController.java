package open.digytal.controller;

import java.util.Date;

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
import open.digytal.util.DataHora;

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
			Date vencimento = lancamento.getParcelamento().getVencimento();
			String[] intervalo = lancamento.getParcelamento().getConfiguracao().split("-");
			Integer inicio=Integer.valueOf(intervalo[0].trim());
			Integer fim = Integer.valueOf(intervalo[1].trim());
			Integer parcelas = 1+(fim-inicio);
			for (int numero = inicio; numero <= parcelas; numero++) {
				Parcela parcela = new Parcela();
				parcela.setLancamento(lancamento);
				parcela.setNumero(numero);
				parcela.setVencimento(vencimento);
				Double valor=lancamento.getParcelamento().isRateio()?(lancamento.getValor() / parcelas):lancamento.getValor();
				parcela.setValor(valor);
				vencimento=Calendario.rolarMes(vencimento, 1);
				lancamento.getParcelamento().addParcela(parcela);
			}
		}else {
			if (lancamento.getTipoMovimento()==TipoMovimento.T) {
				//A magia está aqui
				Lancamento transferencia=lancamento.copia();
				repository.save(transferencia);
				Conta destino = transferencia.getConta();
				destino.setSaldoAtual(destino.getSaldoAtual() + transferencia.getValor());
				contaRepository.save(destino);
			}
			Conta conta = lancamento.getConta();
			conta.setSaldoAtual(conta.getSaldoAtual() + lancamento.getValor());
			contaRepository.save(conta);
		}
		repository.save(lancamento);
	}
}
