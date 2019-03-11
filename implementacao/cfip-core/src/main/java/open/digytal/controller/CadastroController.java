package open.digytal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import open.digytal.model.entity.EntidadeConta;
import open.digytal.model.entity.EntidadeNatureza;
import open.digytal.model.enums.TipoMovimento;
import open.digytal.repository.ContaRepository;
import open.digytal.repository.NaturezaRepository;
import open.digytal.service.CadastroService;

@Controller
public class CadastroController implements CadastroService {
	@Autowired
	private ContaRepository repository;

	@Autowired
	private NaturezaRepository naturezaRepository;

	public List<EntidadeConta> listarContas(String login) {
		return repository.listar(login);
	}

	public List<EntidadeConta> listarCartoesCredito(String login) {
		return repository.listarCartoesCredito(login);
	}

	public List<EntidadeConta> listarTodas(String login) {
		return repository.listarTodas(login);
	}

	public List<EntidadeConta> listarContas(Integer id) {
		return repository.listar(id);
	}

	public List<EntidadeConta> listarContas(String login, String nome) {
		return repository.listar(login, nome);
	}

	public List<EntidadeNatureza> listarNaturezas(String login, String nome) {
		return naturezaRepository.listar(login, nome);
	}

	public List<EntidadeNatureza> listarNaturezas(String login, TipoMovimento tipo) {
		return naturezaRepository.listar(login, tipo);
	}

	public List<EntidadeNatureza> listarNaturezas(String login) {
		return naturezaRepository.listar(login);
	}
}
