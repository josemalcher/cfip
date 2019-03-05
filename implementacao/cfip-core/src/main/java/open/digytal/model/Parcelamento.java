package open.digytal.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class Parcelamento {
	@Temporal(TemporalType.DATE)
	@Column(nullable=true,name="dt_primeiro_vencto")
	private Date primeiroVencimento;
	@Column(length=4,nullable=true)
	private Integer primeiraParcela;
	@Column(length=4,nullable=true)
	private Integer ultimaParcela;
	@Column(nullable=true)
	private boolean rateio;
	@Column(nullable=true)
	private boolean quitado;
	@Column(length=7,precision=2,nullable=true)
	private Double restante;
	@OneToMany(mappedBy="lancamento",cascade=CascadeType.PERSIST)
	private List<Parcela> parcelas;
	
	public Parcelamento() {
		this.parcelas=new ArrayList<Parcela>();
	}
	public List<Parcela> getParcelas() {
		return parcelas;
	}
	public void addParcela(Parcela parcela) {
		parcelas.add(parcela);
	}
	public Date getPrimeiroVencimento() {
		return primeiroVencimento;
	}
	public void setPrimeiroVencimento(Date primeiroVencimento) {
		this.primeiroVencimento = primeiroVencimento;
	}
	public Double getRestante() {
		return restante;
	}
	public void setRestante(Double restante) {
		this.restante = restante;
	}
	public boolean isRateio() {
		return rateio;
	}
	public void setRateio(boolean rateio) {
		this.rateio = rateio;
	}
	public boolean isQuitado() {
		return quitado;
	}
	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
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
	public Integer getNumeroParcelas() {
		return 1 + ((ultimaParcela==null?0:ultimaParcela) - (primeiraParcela==null?0:primeiraParcela));
	}
	
}
