package open.digytal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuracao {
	public static final String SOFTWARE_HOUSE="software_house";
	public static final String SOFTWARE="software";
	
	public static final String ARQUIVO="config.properties";
	public static final String STATUS="status";
	public static final String VERSAO="versao"; 
	public static final String AMBIENTE="ambiente"; 
	public static final String CONEXAO="conexao";
	public static final String PROFILE="prfile";
	public static final String DB_DRIVER="db.driver";
	public static final String DB_URL="db.url";
	public static final String DB_USER="db.user";
	public static final String DB_PASS="db.pass";
	public static final String DB_DIALECT="db.dialect";
	public static final String DB_DDL="db.ddl";
	public static final String DB_SHOWSQL="db.showsql";
	
	
	public static final String NOK="NOK";
	public static final String OK="OK";
	
	public static final String CONF_LOCAL="LOCAL";
	public static final String CONF_SERVER="SERVER";
	public static final String CONF_API="API";
	
	private String conexao;
	private String profile;
	private Sistema sistema;
	private String ambiente;
	private String versao;
	private String dbUrl;
	private String dbUser;
	private String dbPass;
	private String status;
	
	public static String EMPRESA = System.getProperty(SOFTWARE_HOUSE);;
	private Configuracao() {
		
	}
	private Configuracao(String conexao, String dbUrl, String dbUser, String dbPass) {
		this();
		this.versao="1.0";
		this.status="NOK";
		this.conexao = conexao;
		this.dbUrl = dbUrl;
		this.dbUser = dbUser;
		this.dbPass = dbPass;
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
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public Sistema getSistema() {
		return sistema;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public String getVersao() {
		return versao;
	}
	public String getConexao() {
		return conexao;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile() {
		this.profile = (this.conexao.equals(CONF_API))?"API":"JPA";
	}
	public String getDbUrl() {
		String sigla = sistema.getSigla();
		if(conexao.equals(CONF_LOCAL))
			dbUrl = String.format("jdbc:hsqldb:file:/%s/%s/database/%sdb", EMPRESA, sigla,sigla) ;
			
		else if(conexao.equals(CONF_SERVER))
			dbUrl = String.format("datasource:postgresql://localhost:5432/%sdb", sigla) ;
		
		return dbUrl;
	}
	public String getConfigDir(){
		return getConfigDir(sistema);
	}
	private static String getConfigDir(Sistema sistema){
		return String.format("/%s/%s/conf", EMPRESA,sistema.getSigla());
	}
	public String getBackupDir(){
		return String.format("/%s/%s/conf", EMPRESA, sistema.getSigla());
	}
	public String getDbUser() {
		return dbUser;
	}
	public String getDbPass() {
		return dbPass;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDbShowSql() {
		return "false";
	}
	public String getDbDriver() {
		if(dbUrl.contains("postgresql"))
			return "org.postgresql.Driver";
		else
			return "org.hsqldb.jdbcDriver";
	}
	public String getDbDdl() {
		if(dbUrl.contains("postgresql"))
			return "update"; // DEV
		else
			return "update";
	}
	public String getDbDialect() {
		if(dbUrl.contains("postgresql"))
			return "org.hibernate.dialect.PostgreSQLDialect";
		else
			return "org.hibernate.dialect.HSQLDialect";
	}
	public static Configuracao getInstance(Sistema sistema) {
		Configuracao config = new Configuracao();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(getArquivoConfiguracao(sistema));
			prop.load(input);
			config.conexao = prop.getProperty(CONEXAO);
			config.setProfile();
			//...
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return config;
	}
	
	public static Configuracao LOCAL = new Configuracao(CONF_LOCAL,String.format("jdbc:hsqldb:file:/%s/%s/database/%sdb", EMPRESA, Sistema.CFIP.getSigla(),Sistema.CFIP.getSigla()) ,"sa","sa");
	public static Configuracao SERVER = new Configuracao(CONF_SERVER,String.format("datasource:postgresql://localhost:5432/%sdb", Sistema.CFIP.getSigla()), "postgres","postgres");
	
	public static Configuracao[] CONFIGURACOES={LOCAL,SERVER};

	public static boolean iniciarConfiguracao(Sistema sistema){
		return !getArquivoConfiguracao(sistema).exists();
	}
	private static File getArquivoConfiguracao(Sistema sistema) {
		File diretorio = new File(getConfigDir(sistema));
		File arquivo = new File(diretorio, Configuracao.ARQUIVO);
		return arquivo;
	}
}
