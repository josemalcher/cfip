package open.digytal.model;

public enum Categoria {
	R("REMUNERACAO"),
	D("DESPESA"),
	A("ALIMENTACAO"),
	H("HABITACAO"),
	S("SAUDE"),
	E("EDUCACAO"),
	G("HIGIENE"),
	P("ESPORTE"),
	L("LAZER"),
	V("VIAGEM"),
	C("COMUNICACAO"),
	T("TRANSPORTE"),
	M("PATRIMONIO"),
	I("INVESTIMENTO"),
	CF("CUSTO FIXO"),
	CO("CUSTO OPERACIONAL"),
	O("OBRIGACOES"),
	B("TRIBUTOS"),
	Z("TRANSACOES"),
	;
	private String nome;
	private Categoria(String nome) {
		this.nome=nome;
	}
	public String getNome() {
		return nome;
	}
}
