package br.edu.HortifrutiProjeto.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.edu.HortifrutiProjeto.model.Administrador;

@Stateful
public class UsuarioDAO {

	//Criar os objetos sob demanda de forma automática
	@Inject
    private EntityManager em;
	
	public Administrador findById(Long id) {
        return em.find(Administrador.class, id);
    }
	
	public List<Administrador> findAll(){
		//Cria objeto que fará consulta
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Retorno é da classe Usuario
        CriteriaQuery<Administrador> criteria = cb.createQuery(Administrador.class);
        //From usuario
        Root<Administrador> administrador = criteria.from(Administrador.class);
        //Select todos os usuários
        criteria.select(administrador);//.orderBy(cb.asc(usuario.get("usuario")));
        //Executa a consulta e traz o retorno
        return em.createQuery(criteria).getResultList();
	}
	
	public List<Administrador> findAllHQL() {
	    return em.createQuery("SELECT a FROM Administrador a ", 
	    		Administrador.class).getResultList();      
	}
	
	public void save(Administrador u) {
		em.persist(u);
	}
	
}
