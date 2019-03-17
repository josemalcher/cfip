package open.digytal.model;

import java.util.Date;

import open.digytal.model.enums.TipoMovimento;

public class Lancamentos {
	private Integer id;
	private String conta;
	private Date data;
	private String natureza;
	private String descricao;
	private Double valor;
	private Double restante;
	private TipoMovimento tipoMovimento;
	private Integer primeiraParcela;
	private Integer ultimaParcela;
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
	public Double getRestante() {
		return restante;
	}
	public void setRestante(Double restante) {
		this.restante = restante;
	}
	public Integer getPrimeiraParcela() {
		return primeiraParcela;
	}
	public void setPrimeiraParcela(Integer primeiraParcela) {
		this.primeiraParcela = primeiraParcela;
	}
	public void setUltimaParcela(Integer ultimaParcela) {
		this.ultimaParcela = ultimaParcela;
	}
	public Integer getUltimaParcela() {
		return ultimaParcela;
	}
	@Override
	public String toString() {
		return "Lancamentos [id=" + id + ", conta=" + conta + ", data=" + data + ", natureza=" + natureza
				+ ", descricao=" + descricao + ", valor=" + valor + ", restante=" + restante + ", tipoMovimento="
				+ tipoMovimento + ", primeiraParcela=" + primeiraParcela + ", ultimaParcela=" + ultimaParcela + "]";
	}
	
}
