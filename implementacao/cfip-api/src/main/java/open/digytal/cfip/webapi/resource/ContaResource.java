package open.digytal.cfip.webapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.model.Conta;
import open.digytal.repository.ContaRepository;

@RestController
@RequestMapping("/contas")
public class ContaResource {
	@Autowired
	private ContaRepository repository;
	@GetMapping
	public List<Conta> listar(){
		return repository.findAll();
	}
}
