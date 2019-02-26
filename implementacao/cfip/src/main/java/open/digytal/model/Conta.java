package open.digytal.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import open.digytal.util.Calendario;
import open.digytal.util.DataHora;

@Entity
@Table(name="tb_conta")
public class Conta implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=50,nullable=false)
	private String nome;
	
	@Column(length=10,nullable=false)
	private String sigla;
	
	@Column(name="aplicacao", length=1,nullable=false)
	private boolean aplicacao;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_inicial",nullable=false)
	private Date dataInicial;
	
	@Column(name="saldo_inicial",length=8,precision=2,nullable=false)
	private Double saldoInicial;
	
	@Column(name="saldo_atual",length=8,precision=2,nullable=false)
	private Double saldoAtual;
	
	@Column(name="cartao_credito", length=1,nullable=false)
	private boolean cartaoCredito;
	
	@Column(name="dia_pagto", length=2,nullable=false)
	private Integer diaPagamento;
	
	public Conta() {
		this.saldoAtual=0.0d;
		this.saldoInicial=0.0d;
		this.dataInicial = new Date();
		this.diaPagamento=0;
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
	public Date getDataPagamento() {
		Date dataPagamento = Calendario.data(diaPagamento,DataHora.mes(),DataHora.ano());
		if(!cartaoCredito)
			return dataPagamento;
		else {
			int diaCompra = DataHora.dia();
			int diaFechamento = diaPagamento -10;
			if(diaCompra>diaFechamento)
				return Calendario.adicionarMes(dataPagamento, 1);
			else
				return dataPagamento;
		}
	}
	public static void main(String[] args) {
		Conta c = new Conta();
		c.setDiaPagamento(20);
		c.setCartaoCredito(true);
		System.out.println(c.getDataPagamento());
	}
}
