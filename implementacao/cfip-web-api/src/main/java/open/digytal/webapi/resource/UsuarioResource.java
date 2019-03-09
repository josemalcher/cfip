package open.digytal.webapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.controller.UsuarioController;
import open.digytal.model.acesso.Usuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	@Autowired
	private UsuarioController service;
	
	@PostMapping
	public void incluir(@RequestBody Usuario usuario) {	
		service.incluir(usuario);
	}
}
