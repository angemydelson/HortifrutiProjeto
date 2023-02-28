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

import br.edu.projeto.model.Venda;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados
@Stateful
public class VendaDAO implements Serializable{

	@Inject
	//Cria a conexão e controla a transação com o SGBD (usado pelo Hibernate)
    private EntityManager em;
	
	public Venda encontrarId(Integer id) {
        return em.find(Venda.class, id);
    }
	
	//Query usando a API Criteria do Hibernate
	//Indicada para consultas complexas
	public Boolean ehVendaUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Venda> criteria = cb.createQuery(Venda.class);
        Root<Venda> venda = criteria.from(Venda.class);
        criteria.select(venda);
        criteria.where(cb.like(venda.get("venda"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	//Query usando a linguagem HQL do Hibernate
	//Idnicada para consultas simples
	public List<Venda> listarTodos() {
	    return em.createQuery("SELECT a FROM Venda a ", Venda.class).getResultList();      
	}
	
	public void salvar(Venda u) {
		em.persist(u);
	}
	
	public void atualizar(Venda u) {
		em.merge(u);
	}
	
	public void excluir(Venda u) {
		em.remove(em.getReference(Venda.class, u.getId()));
	}
	public List<Venda> buscarPorTermo(String termo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Venda> cq = cb.createQuery(Venda.class);
        Root<Venda> root = cq.from(Venda.class);
        Predicate condicao = cb.like(root.get("codigoProduto"), "%" + termo + "%");
        cq.where(condicao);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }
}
