package open.digytal.service;

import open.digytal.model.Usuario;

public interface UsuarioService{
	Usuario buscar(String login);
	Usuario incluir(Usuario usuario);
	boolean validarSenha(String senhaInformada, String senhaCriptografada);
}
