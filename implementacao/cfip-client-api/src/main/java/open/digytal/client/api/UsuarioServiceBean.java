package open.digytal.client.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import open.digytal.model.Login;
import open.digytal.model.Sessao;
import open.digytal.model.Usuario;
import open.digytal.service.UsuarioService;
import open.digytal.util.config.Configuracao;

@Component
@Profile(Configuracao.PROFILE_API)
public class UsuarioServiceBean extends ClientResource implements UsuarioService {
	@Autowired
	private Sessao sessao;
	@Autowired
	private ObjectMapper mapper;
	@Override
	public Sessao login(String usuario, String senha) {
		try {
			Login login = new Login();
			login.setUsername(usuario);
			login.setPassword(senha);
			String token = post(String.class, login, "login");
			Claims claims = getClaims(token);
			atualizarSessao(claims,token);
			return this.sessao;
		} catch (HttpClientErrorException e) {
			return null;
		}
	}
	private void atualizarSessao(Claims claims,String token) {
		Sessao sessao= mapper.convertValue(claims.get(Sessao.KEY),Sessao.class);
		this.sessao.setExpiracao(sessao.getExpiracao());
		this.sessao.setUsuario(sessao.getUsuario());
		this.sessao.setToken(token);
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
