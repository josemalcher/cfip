package open.digytal.util;

import java.util.ArrayList;
import java.util.List;

public class Filtros {
	public static final String PARECIDO ="LIKE";
    public static final String IGUAL="=";
    public static final String MAIOR_IGUAL=">=";
    public static final String MENOR_IGUAL="<=";
    
    public static final String ONDE ="WHERE";
    public static final String OU ="OR";
    public static final String E ="AND";

    public static final String ORDEM ="ORDER BY";
    
    private List<Filtro> filtros;
    private String juncao;
    private static Filtros instance;
    public Filtros(){
    	filtros = new ArrayList<Filtro>();
    }
    public List<Filtro> lista() {
		List<Filtro> lista = instance.filtros;
    	instance=null;
		return lista;
	} 
    public Filtro filtro() {
		Filtro filtro = instance.filtros.get(0);
    	instance=null;
		return filtro;
	} 
    
    public static Filtros igual(String campo, Object valor) {
    	return instance(campo,IGUAL,valor);
    }
    public static Filtros parecido(String campo, Object valor) {
    	return instance(campo,PARECIDO,valor);
    }
    public static Filtros e() {
    	instance.juncao=E;
    	return instance;
    }
    public static Filtros ou() {
    	instance.juncao=OU;
    	return instance;
    }
    private static Filtros instance(String campo,String operador, Object valor) {
    	if(instance==null) {
    		instance = new Filtros();
    		instance.juncao=ONDE;
    	}
    	if(valor!=null && valor.toString().trim().length() > 0)
    		instance.filtros.add(filtro(instance.juncao, campo, operador, valor));
    	
    	instance.juncao=E;
    	return instance;
    }
    private static Filtro filtro(String juncao, String campo, String operador, Object valor) {
        return filtro(juncao, campo, operador,valor,false);
    }
    private static Filtro filtro(String juncao, String campo, String operador, Object valor,boolean ordem){
        //if(ordem || (valor!=null && valor.toString().trim().length() > 0)) {
        if(PARECIDO.equals(operador)) {
            valor="%"+ valor.toString() + "%";
        }
        Filtro filtro = new Filtro();
        filtro.setJuncao(juncao);
        filtro.setCampo(campo);
        filtro.setOperador(operador);
        filtro.setValor(valor);
        filtro.setOrdem(ordem);
        return filtro;
        //}
    }
    public static List<Filtro> filtros(String criterio){
    	System.out.println(criterio);
    	return null;
    }
}
