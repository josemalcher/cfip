package open.digytal.model;

import java.util.Date;

public class Conta {
	protected Integer id;
	protected String nome;
	protected String sigla;
	protected boolean aplicacao;
	protected Date dataInicial;
	protected Double saldoInicial;
	protected Double saldoAtual;
	protected boolean cartaoCredito;
	protected Integer diaPagamento;
	protected Integer diaFechamento;
	protected String login;
	public Conta() {
		this.saldoAtual=0.0d;
		this.saldoInicial=0.0d;
		this.dataInicial = new Date();
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public Integer getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public boolean isAplicacao() {
		return aplicacao;
	}
	public void setAplicacao(boolean aplicacao) {
		this.aplicacao = aplicacao;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}
	public Double getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public Double getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(Double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	public boolean isCartaoCredito() {
		return cartaoCredito;
	}
	public void setCartaoCredito(boolean cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}
	public Integer getDiaPagamento() {
		return diaPagamento;
	}
	public void setDiaPagamento(Integer diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	public Integer getDiaFechamento() {
		return diaFechamento;
	}
	public void setDiaFechamento(Integer diaFechamento) {
		this.diaFechamento = diaFechamento;
	}
	
}
