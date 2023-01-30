package br.edu.HortifrutiProjeto.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.HortifrutiProjeto.model.Vendedor;

@Stateful
public class VendedorDAO {

	//Criar os objetos sob demanda de forma automática
	@Inject
    private EntityManager em;
	
	public Vendedor findById(Long id) {
        return em.find(Vendedor.class, id);
    }
	
	public List<Vendedor> findAll(){
		//Cria objeto que fará consulta
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Retorno é da classe Usuario
        CriteriaQuery<Vendedor> criteria = cb.createQuery(Vendedor.class);
        //From usuario
        Root<Vendedor> vendedor = criteria.from(Vendedor.class);
        //Select todos os usuários
        criteria.select(vendedor);//.orderBy(cb.asc(usuario.get("usuario")));
        //Executa a consulta e traz o retorno
        return em.createQuery(criteria).getResultList();
	}
	
	public List<Vendedor> findAllHQL() {
	    return em.createQuery("SELECT a FROM Vendedor a ", 
	    		Vendedor.class).getResultList();      
	}
	
	public void save(Vendedor u) {
		em.persist(u);
	}
	
}
