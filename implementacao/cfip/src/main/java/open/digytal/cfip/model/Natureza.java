package open.digytal.cfip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import open.digytal.cfip.model.enums.Categoria;
import open.digytal.cfip.model.enums.TipoMovimento;
import open.digytal.cfip.model.logs.Logavel;

@Entity
@Table(name="tb_natureza")
public class Natureza extends Logavel{

	@Column(nullable=false, length=50)
	private String nome;
	
	@Enumerated(EnumType.STRING) //EnumType.ORDINAL
	@Column(name="tipo_mov_id",nullable=false,length=30)
	private TipoMovimento tipoMovimento;
	
	@Enumerated(EnumType.STRING)
	@Column(name="categoria_id",length=50)
	private Categoria categoria;
	
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
		
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
