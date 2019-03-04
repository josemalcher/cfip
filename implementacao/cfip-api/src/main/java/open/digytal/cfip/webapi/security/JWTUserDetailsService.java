package open.digytal.cfip.webapi.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import open.digytal.model.Usuario;
import open.digytal.repository.UsuarioRepository;

@Component
public class JWTUserDetailsService implements UserDetailsService {

    private UsuarioRepository repository;

    public JWTUserDetailsService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = jwtUser(username);
        if(user!=null)
    		return user;
        else
        	throw new UsernameNotFoundException("Usuário: " + username + " não encontrado");
    }
    private UserDetails jwtUser(String username) {
    	 Optional<Usuario> usuario = this.repository.findByLogin(username);
    	 if(usuario.isPresent()) {
    		 return  new JWTUser(username, usuario.get().getSenha());
    	 }else return null;
    }
}
