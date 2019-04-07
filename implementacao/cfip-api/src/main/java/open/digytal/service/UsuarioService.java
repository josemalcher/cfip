package open.digytal.service;

import open.digytal.model.Sessao;
import open.digytal.model.Usuario;

public interface UsuarioService{
	Sessao login(String login,String senha);
	void incluir(Usuario usuario);
	Usuario buscar(String loging);
}
