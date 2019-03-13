package open.digytal.service;

import open.digytal.model.Usuario;

public interface UsuarioService{
	Usuario login(String login,String senha);
	Usuario incluir(Usuario usuario);
}
