package open.digytal.util;

public class Configuracao {
	private String conexao;
	private String profile;
	private String ambiente;
	private String versao;
	private String url;
	private String user;
	private String pass;
	private String role;
	
	/*private String driver;
	private String dialect;
	private String ddl;
	private String showDdl;*/
	private String status;
	
	public Configuracao() {
		
	}
	public Configuracao(String conexao, String user, String pass) {
		this();
		this.versao="1.0";
		this.status="NOK";
		this.conexao = conexao;
		this.user = user;
		this.pass = pass;
	}
	public String getConexao() {
		return conexao;
	}
	public void setConexao(String conexao) {
		this.conexao = conexao;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getVersao() {
		return versao;
	}
	public void setVersao(String versao) {
		this.versao = versao;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDriver() {
		if(url.contains("postgresql"))
			return "org.postgresql.Driver";
		else
			return "org.hsqldb.jdbcDriver";
	}
	public String getDialect() {
		if(url.contains("postgresql"))
			return "org.hibernate.dialect.PostgreSQLDialect";
		else
			return "org.hibernate.dialect.HSQLDialect";
	}
	public String getDdl() {
		if(url.contains("postgresql"))
			return "update"; // DEV
		else
			return "update";
	}
	public String getShowSql() {
		return "false";
	}
	
	
	
	/*public String getDbDriver() {
		if(url.contains("postgresql"))
			return "org.postgresql.Driver";
		else
			return "org.hsqldb.jdbcDriver";
	}
	public String getDbDdl() {
		if(url.contains("postgresql"))
			return "update"; // DEV
		else
			return "update";
	}
	public String getDbDialect() {
		if(url.contains("postgresql"))
			return "org.hibernate.dialect.PostgreSQLDialect";
		else
			return "org.hibernate.dialect.HSQLDialect";
	}*/
	
}
