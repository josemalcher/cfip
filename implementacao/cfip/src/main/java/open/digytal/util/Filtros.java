package open.digytal.util;

public class Filtros {
   
    public static final String PARECIDO ="LIKE";
    public static final String IGUAL="=";
    public static final String MAIOR_IGUAL=">=";
    public static final String MENOR_IGUAL="<=";
    
    public static final String ONDE ="WHERE";
    public static final String OU ="OR";
    public static final String E ="AND";

    public static final String ORDEM ="ORDER BY";
    
    public static Filtro onde(String campo, Object valor) {
        return onde(campo, PARECIDO, valor);
    }
    public static Filtro ondeIgual(String campo, Object valor) {
        return onde(campo, IGUAL, valor);
    }
    public static Filtro e(String campo, Object valor) {
        return e(campo, PARECIDO, valor);
    }
    
    public static Filtro eIgual(String campo, Object valor) {
        return e(campo, IGUAL, valor);
    }
    public static Filtro eMaiorIgual(String campo, Object valor) {
        return e(campo, MAIOR_IGUAL, valor);
    }
    public static Filtro eMenorIgual(String campo, Object valor) {
        return e(campo, MENOR_IGUAL, valor);
    }
    
    public static Filtro onde(String campo, String operador, Object valor) {
        return filtro(ONDE, campo, operador, valor);
    }
    public static Filtro e(String campo, String operador, Object valor) {
        return filtro(E, campo, operador, valor);
    }
    public static Filtro ou(String campo, String operador, Object valor) {
        return filtro(OU, campo, operador, valor);
    }
    public static Filtro ordem(String campos) {
        return filtro(ORDEM, campos,"",null,true);
    }
    public static Filtro filtro(String juncao, String campo, String operador, Object valor) {
        return filtro(juncao, campo, operador,valor,false);
    }
    public static Filtro filtro(String juncao, String campo, String operador, Object valor,boolean ordem){
        if(ordem || (valor!=null && valor.toString().trim().length() > 0)) {
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
        }else {
            Filtro todos = new Filtro();
            todos.setTodos(true);
            todos.setCampo(campo);
            return todos;
        }
    }
}
