package open.digytal.controller;

import java.util.Collections;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import open.digytal.model.Usuario;
import open.digytal.model.acesso.Role;
import open.digytal.model.acesso.Roles;
import open.digytal.repository.RoleRepository;
import open.digytal.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	@Autowired
	private UsuarioRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder encoder;
	public Usuario findByLogin(String login){
		Optional<Usuario> find = repository.findByLogin(login); 
		if(find.isPresent())
			return find.get();
		else return null;
	}
	public boolean validarSenha(String senhaInformada, String senhaCriptografada) {
		return encoder.matches(senhaInformada, senhaCriptografada);
	}
	@Transactional
    public Usuario incluir(Usuario usuario) {
		Role roleUser = null;
		for(Roles r: Roles.values()) {
        	Role role = roleRepository.findByNome(r.name());
        	if(role==null) {
    			role = new Role(r.name());
    			role=roleRepository.save(role);
    			if(r==Roles.ROLE_USER)
    				roleUser=role;
        	}
        }
    	if(usuario.getRoles().isEmpty())
        	usuario.setRoles(Collections.singleton(roleUser));
        
    	repository.save(usuario);
    	return usuario;
	}
}
