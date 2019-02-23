package open.digytal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	
	@Column(length=8,precision=2,nullable=false)
	private Double saldo;
	
	@Column(name="propria", length=1,nullable=false)
	private boolean propria;
	
	@Column(name="aplicacao", length=1,nullable=false)
	private boolean aplicacao;
	
	public Conta() {
		this.propria = true;
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
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public boolean isPropria() {
		return propria;
	}
	public void setPropria(boolean propria) {
		this.propria = propria;
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
}
