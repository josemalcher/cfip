package open.digytal.service.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import io.jsonwebtoken.Claims;
import open.digytal.client.api.ClientResource;
import open.digytal.model.Login;
import open.digytal.model.Sessao;
import open.digytal.model.Usuario;
import open.digytal.service.UsuarioService;
//http://andreybleme.com/2017-04-01/autenticacao-com-jwt-no-spring-boot/

@Service
public class UsuarioServiceBean extends ClientResource implements UsuarioService {
	@Autowired
	private Sessao sessao;
	@Override
	public Sessao login(String usuario, String senha) {
		try {
			Login login = new Login();
			login.setUsername(usuario);
			login.setPassword(senha);
			String token = post(String.class, login, "login");
			Claims claims = getClaims(token);
			Sessao sessao= claims.get(Sessao.KEY,Sessao.class);
			this.sessao=sessao;
		    return this.sessao;
		} catch (HttpClientErrorException e) {
			return null;
		}
	}

	@Override
	public void incluir(Usuario usuario) {
		usuario = post(Usuario.class, usuario, "usuarios");
	}

	@Override
	public Usuario buscar(String loging) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
