package open.digytal.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import open.digytal.model.enums.TipoMovimento;
import open.digytal.util.DataHora;
import open.digytal.util.Formatador;

@Entity
@Table(name="tb_parcela")
public class EntidadeParcela {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cd_lancto")
	private EntidadeLancamento lancamento;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date vencimento;
	
	@Column(nullable=false,length=6)
	private Integer periodo;
	
	@Column(nullable=false,length=4)
	private Integer numero;
	
	@Column(nullable=false,length=7, precision=2)
	private Double valor;
	
	@Column(nullable=false)
	private boolean compensada;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=true)
	private Date compensacao;
	
	@Transient
	private Double amortizado;
	
	@Transient
	private boolean selecionada;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public EntidadeLancamento getLancamento() {
		return lancamento;
	}
	public void setLancamento(EntidadeLancamento lancamento) {
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
		return lancamento.getDescricao() + " PARC:" + numero + " DE:" + lancamento.getParcelamento().getUltimaParcela();
	}
	@PrePersist
	private void periodo() {
		this.periodo = Integer.valueOf(Formatador.formatar(DataHora.ano(vencimento),"0000") + Formatador.formatar(DataHora.mes(vencimento),"00"));
		this.valor = lancamento.getTipoMovimento()==TipoMovimento.D?valor * -1:valor;
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
