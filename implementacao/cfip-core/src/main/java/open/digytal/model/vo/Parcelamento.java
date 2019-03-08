package open.digytal.model.vo;

import java.util.Date;

public class Parcelamento {
	private Date primeiroVencimento;
	private Integer primeiraParcela;
	private Integer ultimaParcela;
	private boolean rateio;
	public Date getPrimeiroVencimento() {
		return primeiroVencimento;
	}
	public void setPrimeiroVencimento(Date primeiroVencimento) {
		this.primeiroVencimento = primeiroVencimento;
	}
	public Integer getPrimeiraParcela() {
		return primeiraParcela;
	}
	public void setPrimeiraParcela(Integer primeiraParcela) {
		this.primeiraParcela = primeiraParcela;
	}
	public Integer getUltimaParcela() {
		return ultimaParcela;
	}
	public void setUltimaParcela(Integer ultimaParcela) {
		this.ultimaParcela = ultimaParcela;
	}
	public boolean isRateio() {
		return rateio;
	}
	public void setRateio(boolean rateio) {
		this.rateio = rateio;
	}
	
}
