package open.digytal.model;

import java.util.Date;

public class Sessao {
	private Usuario usuario;
	private Date expiracao;
	public static String KEY="SESSAO";
	public Sessao() {
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public boolean isAtiva() {
		return usuario!=null;
	}
	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}
	public Date getExpiracao() {
		return expiracao;
	}
	public String getLogin() {
		return usuario.getLogin();
	}
}
