package open.digytal.model;

import java.util.Date;

public class Parcelas {
	private Integer id;
	private Date vencimento;
	private Integer numero;
	private Double valor;
	
	private Integer lancamento;
	private Integer conta;
	private Integer natureza;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLancamentoId() {
		return lancamentoId;
	}
	public void setLancamentoId(Integer lancamentoId) {
		this.lancamentoId = lancamentoId;
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
	public String getDescricao() {
		return "VER";
	}
	
}
