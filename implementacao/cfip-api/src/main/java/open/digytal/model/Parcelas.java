package open.digytal.model;

import java.util.Date;

public class Parcelas {
	private Integer id;
	private Integer lancamento;
	private Date vencimento;
	private Integer periodo;
	private Integer numero;
	private Double valor;
	private boolean compensada;
	private Date compensacao;
	private Double amortizado;
	private boolean selecionada;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLancamento() {
		return lancamento;
	}
	public void setLancamento(Integer lancamento) {
		this.lancamento = lancamento;
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
	public boolean isCompensada() {
		return compensada;
	}
	public void setCompensada(boolean compensada) {
		this.compensada = compensada;
	}
	public Date getCompensacao() {
		return compensacao;
	}
	public void setCompensacao(Date compensacao) {
		this.compensacao = compensacao;
	}
	public Integer getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public String getDescricao() {
		return "VER";
	}
	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}
	public void setAmortizado(Double amortizado) {
		this.amortizado = amortizado;
	}
	public boolean isSelecionada() {
		return selecionada;
	}
	public Double getAmortizado() {
		return amortizado;
	}
}
