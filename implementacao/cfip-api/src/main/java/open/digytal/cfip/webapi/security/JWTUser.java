package open.digytal.cfip.webapi.security;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTUser implements UserDetails {
	private String username;
	private String password;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Stream.of(new SimpleGrantedAuthority("ADMIN")).collect(Collectors.toList());
	}
	public JWTUser() {
		
	}
	
	public JWTUser(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
