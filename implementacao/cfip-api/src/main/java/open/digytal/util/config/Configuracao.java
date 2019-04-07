package open.digytal.util.config;

import java.io.File;
import java.util.Objects;

public class Configuracao {
	public static final String SECRET=Objects.toString(System.getProperty("app.secret"),"NewCfipAppSecret");
	public static final String ROOT_PATH=System.getProperty("user.dir");
	public static final String CONFIG="application.properties";
	
	public static final String CONF_LOCAL="LOCAL";
	public static final String CONF_SERVER="SERVER";
	public static final String CONF_API="API";
	
	public static final String API_URL="api.url";
	
	public static final String DB_DRIVER="spring.datasource.driverClassName";
	public static final String DB_URL="spring.datasource.url";
	public static final String DB_USER="spring.datasource.username";
	public static final String DB_PASS="spring.datasource.password";
	public static final String DB_DIALECT="spring.jpa.properties.hibernate.dialect";
	public static final String DB_SHOWSQL="spring.jpa.show-sql";
	public static final String DB_DDL="spring.jpa.hibernate.ddl-auto";
	public static final String ACTIVE_PROFILE="spring.profiles.active";
	
	public static final String PROFILE_API="API";
	public static final String PROFILE_DB="!API";
	
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private String dbDialect;
	private String dbDriver;
	private String tipo;
	
	private String apiUrl;
	private Configuracao(String tipo) {
		this.tipo=tipo;
	}
	private Configuracao(String tipo, String apiUrl) {
		this(tipo);
		this.apiUrl=apiUrl;
	}
	private Configuracao(String tipo,String dbUrl, String dbUser, String dbPass,String dbDriver,String dbDialect ) {
		this(tipo);
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPass = dbPass;
		this.dbDriver=dbDriver;
		this.dbDialect=dbDialect;
		
	}
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public String getDbUrl() {
		return dbUrl;
	}
	public String getDbUser() {
		return dbUser;
	}
	public String getDbPass() {
		return dbPass;
	}
	public String getDbShowSql() {
		return "false";
	}
	public String getDbDriver() {
		return dbDriver;
	}
	public String getDbDdl() {
		if(dbUrl.contains("postgresql"))
			return "update"; // DEV
		else
			return "update";
	}
	public String getDbDialect() {
		return dbDialect;
	}
	public String getTipo() {
		return tipo;
	}
	
	public static Configuracao LOCAL = new Configuracao(CONF_LOCAL,String.format("jdbc:hsqldb:file:/%s/%s/database/%sdb", "opendigytal", "cfip","cfip") ,"sa","sa","org.hsqldb.jdbcDriver","org.hibernate.dialect.HSQLDialect");
	public static Configuracao SERVER = new Configuracao(CONF_SERVER,String.format("datasource:postgresql://localhost:5432/%sdb", "cfip"), "postgres","postgres","org.postgresql.Driver","org.hibernate.dialect.PostgreSQLDialect");
	public static Configuracao API = new Configuracao(CONF_API,String.format("localhost:8080/%s-web-api/", "cfip"));
	
	public static Configuracao[] CONFIGURACOES={LOCAL,SERVER,API};

	public static boolean iniciarConfiguracao(){ //empresa e sistema
		System.out.println("Iniciando a aplicação em: " + ROOT_PATH);
		System.setProperty("app.home", ROOT_PATH);
		System.setProperty("app.name", "cfip");
		System.setProperty("jasypt.encryptor.password",SECRET); //SECRET
		return !getArquivoConfiguracao().exists();
	}
	public static File getArquivoConfiguracao() {
		File arquivo = new File(ROOT_PATH, CONFIG);
		return arquivo;
	}
	
}
