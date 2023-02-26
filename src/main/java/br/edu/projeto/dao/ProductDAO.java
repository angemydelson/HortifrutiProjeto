package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import javax.persistence.criteria.Predicate;

import br.edu.projeto.model.Product;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados
@Stateful
public class ProductDAO implements Serializable{

	@Inject
	//Cria a conexão e controla a transação com o SGBD (usado pelo Hibernate)
    private EntityManager em;
	
	public Product encontrarId(Integer id) {
        return em.find(Product.class, id);
    }
	
	//Query usando a API Criteria do Hibernate
	//Indicada para consultas complexas
	public Boolean ehProductUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> product = criteria.from(Product.class);
        criteria.select(product);
        criteria.where(cb.like(product.get("product"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	//Query usando a linguagem HQL do Hibernate
	//Idnicada para consultas simples
	public List<Product> listarTodos() {
	    return em.createQuery("SELECT a FROM Product a ", Product.class).getResultList();      
	}
	
	public void salvar(Product u) {
		em.persist(u);
	}
	
	public void atualizar(Product u) {
		em.merge(u);
	}
	
	public void excluir(Product u) {
		em.remove(em.getReference(Product.class, u.getId()));
	}
	public List<Product> buscarPorTermo(String termo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        Predicate condicao = cb.like(root.get("nome"), "%" + termo + "%");
        cq.where(condicao);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }
}
