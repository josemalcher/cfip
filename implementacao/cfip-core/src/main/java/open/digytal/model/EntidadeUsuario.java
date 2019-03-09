package open.digytal.model;

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
import open.digytal.model.acesso.EntidadeRole;

@Entity
@Table(name = "tb_usuario")
public class EntidadeUsuario {
	@Id
	@Column(length=15)
    private String login;
	@Column(length=100)
    private String senha;
	@Column(length=50)
    private String nome;
	@Column(length=70)
    private String email;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "login",nullable=false), inverseJoinColumns = @JoinColumn(name = "nome",nullable=false))
	private Set<EntidadeRole> roles = new HashSet<>();
	
	public void setRoles(Set<EntidadeRole> roles) {
		this.roles = roles;
	}
	public Set<EntidadeRole> getRoles() {
		return roles;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
    
}
