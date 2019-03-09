package open.digytal.model.acesso;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_usuario")
public class EntidadeUsuario extends Usuario {
	private Set<EntidadeRole> roles = new HashSet<>();
	
	@Id
	@Column(length=15)
	public String getLogin() {
		return login;
	}
	@Column(length=100)
	public String getSenha() {
		return senha;
	}
	@Column(length=50)
	public String getNome() {
		return nome;
	}
	@Column(length=70)
	public String getEmail() {
		return email;
	}
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "login",nullable=false), inverseJoinColumns = @JoinColumn(name = "nome",nullable=false))
	public Set<EntidadeRole> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<EntidadeRole> roles) {
		this.roles = roles;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}
