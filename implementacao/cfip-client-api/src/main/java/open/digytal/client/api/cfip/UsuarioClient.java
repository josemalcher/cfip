package open.digytal.client.api.cfip;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import open.digytal.model.Usuario;
import open.digytal.service.Services;
import open.digytal.service.UsuarioService;
@Service
@Profile(Services.API)
public class UsuarioClient extends ClientResource implements UsuarioService{

	@Override
	public Usuario login(String login,String senha) {
		// TODO Auto-generated method stub
		return null;
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
