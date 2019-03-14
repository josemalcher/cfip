package open.digytal.client.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Login;
import open.digytal.model.Sessao;
import open.digytal.model.Usuario;
import open.digytal.service.UsuarioService;
@Service
//@Profile(Services.API)
public class UsuarioClient extends ClientResource implements UsuarioService{
	
	@Override
	public Sessao login(String usuario,String senha) {
		Login login  = new Login();
		login.setUsername(usuario);
		login.setPassword(senha);
		Sessao credencial = Sessao.newInstance(post(Sessao.class, login, "login"));
		return credencial;
	}

	@Override
	public Usuario incluir(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	protected ParameterizedTypeReference getListaType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ParameterizedTypeReference getEntidadeType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getResource() {
		// TODO Auto-generated method stub
		return null;
	}

   
}
