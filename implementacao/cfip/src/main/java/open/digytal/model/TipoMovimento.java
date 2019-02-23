package open.digytal.model;

import java.io.Serializable;

public enum TipoMovimento implements Serializable {
	C(false,"CREDITO"), 
	D(false,"DEBITO"), 
	T(true,"TRANSFERENCIA"); 
	
	private boolean tranferencia;
	private String nome;
	private TipoMovimento(boolean tranferencia,String nome) {
		this.tranferencia=tranferencia;
		this.nome=nome;
	}
	public boolean isTranferencia() {
		return tranferencia;
	}
	public String getNome() {
		return nome;
	}
}
