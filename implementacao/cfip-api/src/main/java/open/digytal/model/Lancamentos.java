package open.digytal.model;

import open.digytal.model.enums.TipoMovimento;

public class Lancamentos {
	private Integer id;
	private String conta;
	private String natureza;
	private String descricao;
	private Double valor;
	private TipoMovimento tipoMovimento;
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getNatureza() {
		return natureza;
	}
	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	@Override
	public String toString() {
		return "Lancamentos [id=" + id + ", conta=" + conta + ", natureza=" + natureza + ", descricao=" + descricao
				+ ", valor=" + valor + "]";
	}
	
	
}
