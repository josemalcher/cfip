package open.digytal.webapi.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.controller.UsuarioController;
import open.digytal.model.EntidadeUsuario;
import open.digytal.webapi.secutiry.model.NovoUsuario;

@RestController
@RequestMapping("/usuarios")
public class UsuarioResource {
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioController service;
	
	@PostMapping
	public void incluir(@RequestBody NovoUsuario usuario) {
		EntidadeUsuario entidade = new EntidadeUsuario();
		entidade.setNome(usuario.getNome());
		entidade.setLogin(usuario.getLogin());
		entidade.setEmail(usuario.getEmail());
		entidade.setSenha(encoder.encode(usuario.getSenha()));
		service.incluir(entidade);
	}
}
