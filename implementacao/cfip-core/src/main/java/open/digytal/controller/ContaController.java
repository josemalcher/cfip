package open.digytal.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import open.digytal.model.Conta;
import open.digytal.model.Lancamento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.persistence.Controllers;
import open.digytal.service.ContaService;

@Controller
public class ContaController extends Controllers<Conta> implements ContaService {
	/*
	 * @Autowired private ContaRepository repository; public List<Conta>
	 * listarContas(String login){ return repository.listarContas(login); } public
	 * List<Conta> listarTodas(String login){ return repository.listarTodas(login);
	 * } public List<Conta> listarCartoesCredito(String login){ return
	 * repository.listarCartoesCredito(login); } public List<Conta> listar(Integer
	 * id){ return repository.listar(id); } public List<Conta> listar(String login,
	 * String nome){ return repository.listar(login,nome); } public List<Lancamento>
	 * extrato(Integer contaId,Date dataInicio){ return repository.extrato(contaId,
	 * dataInicio); }
	 */
	
}
