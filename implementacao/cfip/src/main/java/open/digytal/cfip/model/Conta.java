package open.digytal.cfip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import open.digytal.cfip.model.logs.Logavel;

@Entity
@Table(name="tb_conta")
public class Conta extends Logavel {
	
	@Column(length=50,nullable=false)
	private String nome;
	
	@Column(length=8,precision=2,nullable=false)
	private Double saldo;
	
	@Column(name="cartao_cred", length=1,nullable=false)
	private boolean cartaoCredito;
	
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
	
	
}
