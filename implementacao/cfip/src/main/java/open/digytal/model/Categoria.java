package open.digytal.model;

public enum Categoria {
	A("ALIMENTACAO"), //0
	;
	private String nome;
	private Categoria(String nome) {
		this.nome=nome;
	}
	public String getNome() {
		return nome;
	}
}
