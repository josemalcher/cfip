package open.digytal.service;

import open.digytal.model.acesso.Usuario;

public interface UsuarioService extends Services<Usuario> {
	void incluir(Usuario usuario);
}
