package open.digytal.cfip.model.enums;

public enum Categoria {
	A("ALIMENTACAO"), //0
	S("SALARIO"), //1
	;
	private String nome;
	private Categoria(String nome) {
		this.nome=nome;
	}
	public String getNome() {
		return nome;
	}
}
