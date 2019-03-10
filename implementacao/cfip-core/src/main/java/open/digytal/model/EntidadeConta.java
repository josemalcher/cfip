package open.digytal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import open.digytal.util.Calendario;
import open.digytal.util.DataHora;

@Entity
@Table(name = "tb_conta")
public class EntidadeConta extends Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	@Column(length = 50, nullable = false)
	public String getNome() {
		return nome;
	}

	@Column(length = 10, nullable = false)
	public String getSigla() {
		return sigla;
	}

	@Column(name = "aplicacao", length = 1, nullable = false)
	public boolean isAplicacao() {
		return aplicacao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dt_inicial", nullable = false)
	public Date getDataInicial() {
		return dataInicial;
	}

	@Column(name = "saldo_inicial", length = 8, precision = 2, nullable = false)
	public Double getSaldoInicial() {
		return saldoInicial;
	}

	@Column(name = "saldo_atual", length = 8, precision = 2, nullable = false)
	public Double getSaldoAtual() {
		return saldoAtual;
	}

	@Column(name = "cartao_credito", length = 1, nullable = false)
	public boolean isCartaoCredito() {
		return cartaoCredito;
	}

	@Column(name = "dia_pagto", length = 2)
	public Integer getDiaPagamento() {
		return diaPagamento;
	}

	@Column(name = "dia_fechto", length = 2)
	public Integer getDiaFechamento() {
		return diaFechamento;
	}

	@Column(name = "login", length = 20)
	public String getLogin() {
		return login;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setAplicacao(boolean aplicacao) {
		this.aplicacao = aplicacao;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public void setSaldoInicial(Double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public void setSaldoAtual(Double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public void setCartaoCredito(boolean cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}

	public void setDiaPagamento(Integer diaPagamento) {
		this.diaPagamento = diaPagamento;
	}

	public void setDiaFechamento(Integer diaFechamento) {
		this.diaFechamento = diaFechamento;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Transient
	public Date getDataPagamento() {
		Date dataLancamento = Calendario.data();
		if (!cartaoCredito)
			return dataLancamento;
		else {
			Date dataPagamento = Calendario.data(diaPagamento, DataHora.mes(), DataHora.ano());
			int diaCompra = DataHora.dia(dataLancamento);
			if (diaCompra > diaFechamento)
				return Calendario.adicionarMes(dataPagamento, 1);
			else
				return dataPagamento;
		}
	}

	public void atualizarSaldo(EntidadeLancamento lancamento) {
		Double valor = lancamento.getValor();
		if (lancamento.isPrevisao() && (!lancamento.getConta().isCartaoCredito()))
			valor = 0.0d;
		this.saldoAtual = saldoAtual + (lancamento.getTipoMovimento() == TipoMovimento.D ? valor - 1 : valor);
	}

}
