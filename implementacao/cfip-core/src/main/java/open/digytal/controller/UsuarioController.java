package open.digytal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import open.digytal.model.Usuario;
import open.digytal.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private PasswordEncoder encoder;
	public Usuario findByLogin(String login){
		return repository.findByLogin(login).get();
	}
	public boolean validarSenha(String senhaInformada, String senhaCriptografada) {
		return encoder.matches(senhaInformada, senhaCriptografada);
	}
}
