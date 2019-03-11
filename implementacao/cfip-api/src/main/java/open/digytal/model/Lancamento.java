package open.digytal.model;

import java.util.Date;

import open.digytal.model.enums.TipoMovimento;

public class Lancamento {
	private Integer conta;
	private Integer destino;
	private Integer natureza;
	private String descricao;
	private Double valor;
	private Date data;
	private boolean previsao;
	private Parcelamento parcelamento;
	public Lancamento() {
		this.parcelamento = new Parcelamento();
	}
	public Integer getConta() {
		return conta;
	}
	public void setConta(Integer conta) {
		this.conta = conta;
	}
	public Integer getDestino() {
		return destino;
	}
	public void setDestino(Integer destino) {
		this.destino = destino;
	}
	public Integer getNatureza() {
		return natureza;
	}
	public void setNatureza(Integer natureza) {
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
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public boolean isPrevisao() {
		return previsao;
	}
	public void setPrevisao(boolean previsao) {
		this.previsao = previsao;
	}
	public Parcelamento getParcelamento() {
		return parcelamento;
	}
}
