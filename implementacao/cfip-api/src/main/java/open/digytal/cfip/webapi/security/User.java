package open.digytal.cfip.webapi.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jboss.jandex.TypeTarget.Usage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import open.digytal.model.Usuario;
import open.digytal.model.acesso.Role;

public class User  {
	private String username;
	private String password;
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	static class JWTUser implements UserDetails {
		private String username;
		private String password;
		private List<GrantedAuthority> authorities;
		
		public JWTUser() {
			this.authorities = new ArrayList<GrantedAuthority>();
		}
		public JWTUser(Usuario usuario) {
			this();
			this.username = usuario.getLogin();
			this.password = usuario.getSenha();
			usuario.getRoles().forEach(item->authorities.add(new SimpleGrantedAuthority(item.getNome())));
		}
		public List<GrantedAuthority> getAuthorities() {
			return authorities;
		}
		public String getPassword() {
			return password;
		}

		public String getUsername() {
			return username;
		}

		public boolean isAccountNonExpired() {
			return true;
		}

		public boolean isAccountNonLocked() {
			return true;
		}

		public boolean isCredentialsNonExpired() {
			return true;
		}

		public boolean isEnabled() {
			return true;
		}
		
	}
}
