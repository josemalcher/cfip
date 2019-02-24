package open.digytal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_parcela")
public class Parcela {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@ManyToOne
	@JoinColumn(name="cd_lancto")
	private Lancamento lancamento;
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date vencimento;
	@Column(nullable=false,length=4)
	private Integer numero;
	@Column(nullable=false,length=7, precision=2)
	private Double valor;
	@Column(nullable=false)
	private boolean compensada;
	@Temporal(TemporalType.DATE)
	private Date compensacao;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Lancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(Lancamento lancamento) {
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
	
}
