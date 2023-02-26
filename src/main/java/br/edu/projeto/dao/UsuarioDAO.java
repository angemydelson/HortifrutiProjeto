package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.projeto.model.Usuario;

//Classe DAO responsável pelas regras de negócio envolvendo operações de persistência de dados
//Indica-se a acriação de um DAO específico para cada Modelo

//Anotação EJB que indica que Bean (objeto criado para a classe) será comum para toda a aplicação
//Isso faz com que recursos computacionais sejam otimizados
@Stateful
public class UsuarioDAO implements Serializable{

	@Inject
	//Cria a conexão e controla a transação com o SGBD (usado pelo Hibernate)
    private EntityManager em;
	
	public Usuario encontrarId(Integer id) {
        return em.find(Usuario.class, id);
    }
	
	//Query usando a API Criteria do Hibernate
	//Indicada para consultas complexas
	public Boolean ehUsuarioUnico(String u) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = cb.createQuery(Usuario.class);
        Root<Usuario> usuario = criteria.from(Usuario.class);
        criteria.select(usuario);
        criteria.where(cb.like(usuario.get("usuario"), u));
        if (em.createQuery(criteria).getResultList().isEmpty())
        	return true;
        return false;
    }
	
	//Query usando a linguagem HQL do Hibernate
	//Idnicada para consultas simples
	public List<Usuario> listarTodos() {
	    return em.createQuery("SELECT a FROM Usuario a ", Usuario.class).getResultList();      
	}
	
	public void salvar(Usuario u) {
		em.persist(u);
	}
	
	public void atualizar(Usuario u) {
		em.merge(u);
	}
	
	public void excluir(Usuario u) {
		em.remove(em.getReference(Usuario.class, u.getId()));
	}
	
}
