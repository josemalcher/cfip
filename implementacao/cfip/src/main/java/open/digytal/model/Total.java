package open.digytal.model;

public class Total {
	private Double credito=0.0d;
	private Double debito=0.0d;;
	public void aplicar(boolean credito,Double valor) {
		if(credito)
			creditar(valor);
		else
			debitar(valor);
	}
	public void creditar(Double valor) {
		this.credito += valor;
	}
	public void debitar(Double valor) {
		this.debito -= valor;
	}
	public Double getCredito() {
		return credito;
	}
	public Double getDebito() {
		return debito;
	}
	public Double getSaldo() {
		return credito + debito; //nesta case débito já é negativa
	}
}
