package open.digytal.webapi.secutiry.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import open.digytal.model.acesso.EntidadeUsuario;
import open.digytal.repository.UsuarioRepository;


@Service(value = "userService")
public class JwtUserService implements UserDetailsService{
	
	@Autowired
	private UsuarioRepository repository;

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EntidadeUsuario user = repository.findByLogin(username);
		if(user == null){
			throw new UsernameNotFoundException("Usuário não existe");
		}
		Set<SimpleGrantedAuthority> roles=getAuthority(user);
		return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getSenha(), roles);
	}

	private Set<SimpleGrantedAuthority> getAuthority(EntidadeUsuario user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getNome()));
		});
		return authorities;
	}

	

}
