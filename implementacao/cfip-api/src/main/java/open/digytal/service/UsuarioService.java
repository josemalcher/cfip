package open.digytal.service;

import open.digytal.model.Usuario;

public interface UsuarioService{
	Usuario incluir(Usuario usuario);
	boolean validarSenha(String senhaInformada, String senhaCriptografada);
}
