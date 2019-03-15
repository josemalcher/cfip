package open.digytal.client.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import open.digytal.model.Login;
import open.digytal.model.Sessao;
import open.digytal.model.Usuario;
import open.digytal.service.UsuarioService;

@Service
//@Profile(Services.API)
public class UsuarioClient extends ClientResource implements UsuarioService {

	@Override
	public Sessao login(String usuario, String senha) {
		try {
			Login login = new Login();
			login.setUsername(usuario);
			login.setPassword(senha);
			Sessao credencial = Sessao.newInstance(post(Sessao.class, login, "login"));
			return credencial;
		} catch (HttpClientErrorException e) {
			return null;
		}
	}

	@Override
	public void incluir(Usuario usuario) {
		usuario = post(Usuario.class, usuario, "usuarios");
	}

	

}
