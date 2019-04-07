package open.digytal.model;

import java.util.Date;

public class Sessao {
	private Usuario usuario;
	private Date expiracao;
	private String token;
	public static String KEY="SESSAO";
	public Sessao() {
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setExpiracao(Date expiracao) {
		this.expiracao = expiracao;
	}
	public Date getExpiracao() {
		return expiracao;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}
