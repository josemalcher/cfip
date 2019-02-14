package open.digytal.cfip.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import open.digytal.cfip.model.Conta;

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
}
