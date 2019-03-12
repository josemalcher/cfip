package open.digytal.client.api.cfip;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import open.digytal.service.Services;
@Service
@Profile(Services.API)
public class UsuarioClient extends ClientResource implements UserCache{

	@Override
	public UserDetails getUserFromCache(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putUserInCache(UserDetails user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUserFromCache(String username) {
		// TODO Auto-generated method stub
		
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
