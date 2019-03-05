package open.digytal.model.acesso;

import javax.persistence.*;

@Entity
@Table(name = "tb_role")
public class Role {
	@Id
	@Column(length = 20)
	private String nome;
	public Role() {

	}
	public Role(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
