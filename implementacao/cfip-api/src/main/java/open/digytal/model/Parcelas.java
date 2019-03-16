package open.digytal.model;

import java.util.Date;

public class Parcelas {
	private Integer id;
	private Date vencimento;
	private Integer numero;
	private Double valor;
	
	private Integer lancamento;
	private String conta;
	private String natureza;
	private String descricao;
	
	private Double amortizado;
	private boolean selecionada;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Integer getLancamento() {
		return lancamento;
	}

	public void setLancamento(Integer lancamento) {
		this.lancamento = lancamento;
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
	public Double getAmortizado() {
		return amortizado;
	}
	public void setAmortizado(Double amortizado) {
		this.amortizado = amortizado;
	}
	public boolean isSelecionada() {
		return selecionada;
	}
	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}
	
	
}
