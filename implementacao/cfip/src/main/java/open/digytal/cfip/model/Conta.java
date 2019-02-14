package open.digytal.cfip.model;

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
	
	@Column(length=8,precision=2,nullable=false)
	private Double saldo;
	
	@Column(name="cartao_cred", length=1,nullable=false)
	private boolean cartaoCredito;
	
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
