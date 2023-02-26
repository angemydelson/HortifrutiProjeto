package br.edu.projeto.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.edu.projeto.model.TipoPermissao;

@Stateful
public class TipoPermissaoDAO implements Serializable {

	@Inject
    private EntityManager em;
	
	public TipoPermissao encontrarPermissao(Integer permissaoId) {
        return em.find(TipoPermissao.class, permissaoId);
    }
	
	public List<TipoPermissao> listarTodos() {
	    return em.createQuery("SELECT a FROM TipoPermissao a ", TipoPermissao.class).getResultList();      
	}
}
