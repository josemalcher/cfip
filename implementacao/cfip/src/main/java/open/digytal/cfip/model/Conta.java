package open.digytal.cfip.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tb_conta")
public class Conta implements Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(length=50,nullable=false)
	private String nome;
	
	@Column(length=8,precision=2,nullable=false)
	private Double saldo;
	
	@Column(name="cartao_cred", length=1,nullable=false)
	private boolean cartaoCredito;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_inclusao",nullable=false)
	private Date dataInclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dt_alteracao")
	private Date dataAlteracao;
	
	public Conta() {
		
	}
	@PrePersist
	private void inclusao() {
		this.dataInclusao = new Date();
		System.out.println("A data de inclusao é " + dataInclusao);
	}
	@PreUpdate
	private void alteraca() {
		this.dataAlteracao = new Date();
		//System.out.println("A data de inclusao é " + dataInclusao);
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
	public boolean isCartaoCredito() {
		return cartaoCredito;
	}
	public void setCartaoCredito(boolean cartaoCredito) {
		this.cartaoCredito = cartaoCredito;
	}
	@Override
	public String toString() {
		return "Conta [id=" + id + ", nome=" + nome + ", saldo=" + saldo + ", cartaoCredito=" + cartaoCredito + "]";
	}
	
}
