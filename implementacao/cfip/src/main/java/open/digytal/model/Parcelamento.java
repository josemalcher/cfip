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
	@Column(nullable=true)
	private Date vencimento;
	@Column(length=10,nullable=true)
	private String configuracao;
	@Column(nullable=true)
	private boolean rateio;
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
	
	public Date getVencimento() {
		return vencimento;
	}
	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}
	public String getConfiguracao() {
		return configuracao;
	}
	public void setConfiguracao(String configuracao) {
		this.configuracao = configuracao;
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
}
