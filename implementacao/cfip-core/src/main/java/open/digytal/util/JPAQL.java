package open.digytal.util;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import open.digytal.util.Filtro;

public class JPAQL {
    private EntityManager em;
    private String classe;
    private List<Filtro> filtros;
    public JPAQL onde(){
        return this;
    }
    public JPAQL(EntityManager em,Class classe) {
    	this(em,classe.getPackage().getName() + ".Entidade"+classe.getSimpleName());
    }
    private JPAQL(EntityManager em,String classe) {
        this.classe = classe;
        this.em=em;
    }
    public void setFiltros(List<Filtro> filtros) {
        this.filtros = filtros;
    }
    public void setFiltro(Filtro filtro){
        filtros.add(filtro);
    }
    public String getSql() {
        StringBuilder sql = new StringBuilder("SELECT e FROM " + classe + " e ");
        if(filtros.size() > 0) {
            for (Filtro filtro : filtros) {
                if(!filtro.isOrdem() && !filtro.isTodos()) {
                    String append = String.format(" %s e.%s %s :%s", filtro.getJuncao(), filtro.getCampo(), filtro.getOperador(), filtro.getParametro());
                    sql.append(append);
                }else{
                    sql.append(" ORDER BY " + filtro.getCampo());
                }
            }
        }
        return sql.toString();
    }
    public List listar(){
        System.out.println(getSql());
        Query query = em.createQuery(getSql());
        if(filtros.size() > 0) {
            for (Filtro filtro : filtros) {
                if(!filtro.isOrdem() && !filtro.isTodos())
                    query.setParameter(filtro.getParametro(), filtro.getValor());
            }
        }
        return query.getResultList();
    }
}
