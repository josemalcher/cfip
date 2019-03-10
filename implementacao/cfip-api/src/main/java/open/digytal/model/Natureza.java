package open.digytal.model;

import java.io.Serializable;
public class Natureza implements Serializable {
	protected Integer id;
	protected String nome;
	protected String descricao;
	protected TipoMovimento tipoMovimento;
	protected Categoria categoria;
	protected String login;
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public String getTipoSigla() {
		return  this.tipoMovimento.toString().substring(0, 1);
	}
	public String getNomeSigla() {
		return  nome + " (" + getTipoSigla() + ")";
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return "Natureza [id=" + id + ", nome=" + nome + ", tipoMovimento=" + tipoMovimento + ", categoria=" + categoria
				+ "]";
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
