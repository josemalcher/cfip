package open.digytal.model;

public class Lancamentos {
	private Integer id;
	private String conta;
	private String natureza;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getNatureza() {
		return natureza;
	}
	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}
	@Override
	public String toString() {
		return "Lancamentos [id=" + id + ", conta=" + conta + ", natureza=" + natureza + "]";
	}
	
}
