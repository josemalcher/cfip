package open.digytal.cfip.webapi.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class UsuarioResource {
	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UsuarioRepository dao;
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Usuario> listar() {
		return dao.findAll();
	}

	@PostMapping
	public void incluir(@RequestBody Usuario usuario) {
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		dao.save(usuario);
	}
}
