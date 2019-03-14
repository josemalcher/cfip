package open.digytal.model;

import java.util.Date;

public class Sessao {
	private Usuario usuario;
	private String token;
	private Date inicio;
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getInicio() {
		return inicio;
	}
	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}
	public boolean ativa() {
		return usuario!=null;
	}
	public String getLogin() {
		return usuario.getLogin();
	}
	private static Sessao instance;
	public static Sessao getInstance() {
		return instance;
	}
	public static Sessao  newInstance(Usuario usuario, String token) {
		instance = new Sessao();
		instance.setInicio(new Date());
		instance.setUsuario(usuario);
		instance.setToken(token);
		return instance;
	}
}
