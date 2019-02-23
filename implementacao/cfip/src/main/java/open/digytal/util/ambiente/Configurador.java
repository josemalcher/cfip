package open.digytal.util.ambiente;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import open.digytal.util.Configuracao;
import open.digytal.util.Texto;
import open.digytal.util.model.Credencial;

public class Configurador {
	
	private static Configuracao configuracao;
	private static Credencial credencial;
	public static final String FABRICANTE="fabricante";
	public static final String PRODUTO ="produto";
	public static final String PACOTE_MODELO ="pacotes";
	
	
	public static final String ARQUIVO_CONFIGURACAO="config.properties";
	public static final String VERSAO="versao"; 
	public static final String CONEXAO="conexao";
	public static final String PROFILE="profile";
	public static final String URL="url";
	public static final String USER="user";
	public static final String ROLE="role";
	public static final String PASS="pass";
	
	public static final String DRIVER="driver";
	public static final String DIALECT="dialect";
	public static final String DDL="ddl";
	public static final String SHOWSQL="showsql";
	
	public static final String CONF_LOCAL="LOCAL";
	public static final String CONF_SERVER="SERVER";
	public static final String CONF_API="API";
	
	public static Configuracao LOCAL = new Configuracao(CONF_LOCAL,"sa","sa");
	public static Configuracao SERVER = new Configuracao(CONF_SERVER, "postgres","postgres");
	public static Configuracao API = new Configuracao(CONF_API, "admin","admin");
	
	public static Configuracao[] CONFIGURACOES={LOCAL,SERVER,API};

	public static String getUrlPadrao(Configuracao config) {
		String sigla =getProduto().toLowerCase();
		String url="";
		if(config.getConexao().equals(CONF_LOCAL))
			url = String.format("jdbc:hsqldb:file:/%s/%s/database/%sdb", getFabricante().toLowerCase(), sigla,sigla) ;
			
		else if(config.getConexao().equals(CONF_SERVER))
			url = String.format("datasource:postgresql://localhost:5432/%sdb", sigla) ;
		
		else if(config.getConexao().equals(CONF_API))
			url = String.format("http://localhost:8080/%s-web-api", sigla) ;
		return url;
	}
	public static Credencial getCredencial() {
		return credencial;
	}
	public static void setCredencial(Credencial credencial) {
		Configurador.credencial = credencial;
	}
	public static Configuracao getConfiguracao() {
		if(configuracao==null) {
			configuracao = new Configuracao();
			Properties prop = new Properties();
			InputStream input = null;
			try {
				input = new FileInputStream(getArquivoConfiguracao());
				prop.load(input);
				configuracao.setConexao(prop.getProperty(CONEXAO));
				configuracao.setProfile(prop.getProperty(PROFILE));
				configuracao.setUrl(prop.getProperty(URL));
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
		}
		return configuracao;
	}
	public static String getConfigDir(){
		return getConfigDir(getProduto());
	}
	private static String getConfigDir(String sigla){
		return String.format("/%s/%s/conf", getFabricante().toLowerCase(),sigla.toLowerCase());
	}
	public static String getBackupDir(){
		return String.format("/%s/%s/conf", getFabricante().toLowerCase(), getProduto().toLowerCase());
	}
	public static boolean iniciarConfiguracao(){
		return !getArquivoConfiguracao().exists();
	}
	private static File getArquivoConfiguracao() {
		File diretorio = new File(getConfigDir(getProduto()));
		File arquivo = new File(diretorio, ARQUIVO_CONFIGURACAO);
		return arquivo;
	}
	public static void configurarAmbiente(String fabricante,String produto,String ... pacotes) {
		System.setProperty(FABRICANTE, fabricante);
		System.setProperty(PRODUTO, produto);
		//System.setProperty("spring.config.location", "file:/"+fabricante+"/"+produto+"/conf/config.properties");
		String strPacotes=Texto.concatenar(",", pacotes);
		System.setProperty(PACOTE_MODELO, strPacotes);
	}
	public static String getFabricante() {
		return System.getProperty(FABRICANTE);
	}
	public static String getProduto() {
		return System.getProperty(PRODUTO);
	}
	public static String getPacotes() {
		return System.getProperty(PACOTE_MODELO);
	}
}
