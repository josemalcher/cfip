package open.digytal.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import open.digytal.model.Conta;

@Repository
public class ContaDao {
	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void salvar(Conta conta) {
		em.persist(conta);
	}
	//JPQL
	public List<Conta> listar(){
		return em.createQuery("SELECT e FROM Conta e").getResultList();
	}
	//Criteria
	public List<Conta> listarComCriteria(){
		CriteriaBuilder builder = em.getCriteriaBuilder();
	    CriteriaQuery<Conta> criteria = builder.createQuery(Conta.class);
	    criteria.from(Conta.class);
	    return em.createQuery(criteria).getResultList();
	}
}
