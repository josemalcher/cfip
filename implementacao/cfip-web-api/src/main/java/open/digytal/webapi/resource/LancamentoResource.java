package open.digytal.webapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.controller.LancamentoController;
import open.digytal.model.Lancamento;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {
	@Autowired
	private LancamentoController service;
	
	@PostMapping
	public void incluir(@RequestBody Lancamento objeto) {	
		service.incluir(objeto);
	}
}
