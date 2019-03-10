package open.digytal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tb_natureza")
public class EntidadeNatureza extends Natureza {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	@Column(name="login", length=20)
	public String getLogin() {
		return login;
	}
	
	@Column(nullable=false, length=50)
	public String getNome() {
		return nome;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_mov_id",nullable=false,length=30)
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	@Transient
	public String getNomeSigla() {
		return  nome + " (" + getTipoSigla() + ")";
	}
	@Column(length=50)
	public String getDescricao() {
		return descricao;
	}
	@Enumerated(EnumType.STRING)
	@Column(name="categoria_id",length=50)
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	
}
