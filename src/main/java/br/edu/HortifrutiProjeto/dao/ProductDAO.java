package br.edu.HortifrutiProjeto.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.HortifrutiProjeto.model.Product;

@Stateful
public class ProductDAO {

	//Criar os objetos sob demanda de forma automática
	@Inject
    private EntityManager em;
	
	public Product findById(Long id) {
        return em.find(Product.class, id);
    }
	
	public List<Product> findAll(){
		//Cria objeto que fará consulta
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Retorno é da classe Usuario
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        //From usuario
        Root<Product> product = criteria.from(Product.class);
        //Select todos os usuários
        criteria.select(product);//.orderBy(cb.asc(usuario.get("usuario")));
        //Executa a consulta e traz o retorno
        return em.createQuery(criteria).getResultList();
	}
	
	public List<Product> findAllHQL() {
	    return em.createQuery("SELECT a FROM Product a ", 
	    		Product.class).getResultList();      
	}
	
	public void save(Product u) {
		em.persist(u);
	}
	
}
