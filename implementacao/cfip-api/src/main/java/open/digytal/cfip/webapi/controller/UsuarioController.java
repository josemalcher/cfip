package open.digytal.cfip.webapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import open.digytal.model.Usuario;
import open.digytal.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository dao;
	

	@GetMapping("/new")
	public void novo() {
		Usuario user = new Usuario();
		user.setLogin("admin");
		user.setNome("ADMINISTRADOR");
		user.setEmail("admin@admin.com.br");
		
		String encode = encoder.encode("pass");
		user.setSenha(encode);
		System.out.println(encode);
		dao.save(user);
	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Usuario> clientes() {
		return dao.findAll();
	}
	@PostMapping
	public void incluir(@RequestBody Usuario cliente) {
		dao.save(cliente);
	}
}
