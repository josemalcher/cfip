package open.digytal.service;

import open.digytal.model.acesso.Usuario;

public interface UsuarioService extends Services<Usuario> {
	Usuario incluir(Usuario usuario);
	boolean validarSenha(String senhaInformada, String senhaCriptografada);
}
