package open.digytal.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "tb_role")
public class EntidadeRole {
	@Id
	@Column(length = 20)
	private String nome;
	public EntidadeRole() {

	}
	public EntidadeRole(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
