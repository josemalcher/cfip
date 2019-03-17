package open.digytal.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import open.digytal.model.enums.TipoMovimento;
import open.digytal.util.DataHora;
import open.digytal.util.Formatador;
@Entity
@Table(name="tb_lancamento")
public class EntidadeLancamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "cd_movimento", nullable = false, length = 2)
	private TipoMovimento tipoMovimento;
	
	@ManyToOne
	@JoinColumn(name="cd_conta",nullable=false)
	private EntidadeConta conta;
	
	@ManyToOne
	@JoinColumn(name="cd_destino")
	private EntidadeConta destino;
	
	@ManyToOne
	@JoinColumn(name="cd_origem")
	private EntidadeLancamento origem;
	
	@ManyToOne
	@JoinColumn(name="cd_natureza",nullable=false)
	private EntidadeNatureza natureza;
	
	@Column(nullable=false)
	private String descricao;
	
	@Column(nullable=false,length=7,precision=2)
	private Double valor;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date data;
	
	@Column(length=6,nullable=false)
	private Integer periodo;
	
	@Column(nullable=false)
	private boolean previsao;
	
	@Embedded
	private EntidadeParcelamento parcelamento;
	
	public EntidadeLancamento() {
		this.parcelamento = new EntidadeParcelamento();
	}
	public EntidadeParcelamento getParcelamento() {
		return parcelamento;
	}

	public Integer getPeriodo() {
		return periodo;
	}
	public EntidadeConta getConta() {
		return conta;
	}
	public void setConta(EntidadeConta conta) {
		this.conta = conta;
	}
	public EntidadeNatureza getNatureza() {
		return natureza;
	}
	public void setNatureza(EntidadeNatureza natureza) {
		this.natureza = natureza;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Integer getId() {
		return id;
	}
	public TipoMovimento getTipoMovimento() {
		return tipoMovimento;
	}
	public void setTipoMovimento(TipoMovimento tipoMovimento) {
		this.tipoMovimento = tipoMovimento;
	}
	public boolean isPrevisao() {
		return previsao;
	}
	public void setPrevisao(boolean previsao) {
		this.previsao = previsao;
	}
	public EntidadeConta getDestino() {
		return destino;
	}
	public void setDestino(EntidadeConta destino) {
		this.destino = destino;
	}
	public EntidadeLancamento getOrigem() {
		return origem;
	}
	public void setPeriodo(Integer periodo) {
		this.periodo = periodo;
	}
	public EntidadeLancamento transferencia() {
		EntidadeLancamento copia = new EntidadeLancamento();
		copia.setDescricao("TRANSF.DE: " + conta.getNome() + " - " + descricao);
		copia.setTipoMovimento(TipoMovimento.C);
		copia.setPrevisao(previsao);
		copia.setConta(destino);
		copia.setData(data);
		copia.setNatureza(getNatureza());
		copia.setValor(valor* -1);
		copia.origem=this;
		return copia;
	}
	public EntidadeLancamento compensacao(Double amortizado, int parcela) {
		EntidadeLancamento copia = new EntidadeLancamento();
		copia.setDescricao("COMP: " + getDescricao() + " Parc N°" + parcela + " Origem: " + this.getId());
		copia.setPrevisao(false);
		copia.setConta(getConta());
		copia.setData(new Date());
		copia.setNatureza(getNatureza());
		if(amortizado<0)
			amortizado = amortizado *-1;
		copia.setValor(amortizado);
		copia.setTipoMovimento(getTipoMovimento());
		return copia;
	}
	public void configurar() {
		this.setTipoMovimento(this.getTipoMovimento()==TipoMovimento.T?TipoMovimento.D:this.getTipoMovimento());
		this.setValor(this.getTipoMovimento()==TipoMovimento.D?this.getValor() * -1:this.getValor());
		this.getParcelamento().setRestante(this.isPrevisao() || this.getConta().isCartaoCredito()? this.getValor():0.0d);
		this.setPeriodo(Integer.valueOf(Formatador.formatar(DataHora.ano(this.getData()),"0000") + Formatador.formatar(DataHora.mes(this.getData()),"00")));
	}
	public void atualizarRestante(Double valor) {
		getParcelamento().setRestante(getParcelamento().getRestante() - valor);
	}
}
