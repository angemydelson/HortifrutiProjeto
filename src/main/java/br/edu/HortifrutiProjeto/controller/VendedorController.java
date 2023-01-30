package br.edu.HortifrutiProjeto.controller;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.edu.HortifrutiProjeto.dao.VendedorDAO;
import br.edu.HortifrutiProjeto.model.Vendedor;

//@Model equivale a essas duas anotações
@RequestScoped
//Torna classe disponível na camada de visão (html)
@Named
public class VendedorController {

	@Inject
	//Mensagens de erro para o usuário
    private FacesContext facesContext;

    @Inject
    private VendedorDAO vendedorDAO;

    private Vendedor novoVendedor;
    
    private List<Vendedor> listaVendedores;

    //Executa após instanciar classe UsuarioController, ou seja, 
    //a cada requisição (RequestScoped)
    @PostConstruct
    public void inicializarUsuario() {
    	novoVendedor = new Vendedor();
    	listaVendedores = vendedorDAO.findAllHQL();
    }

    public void register() throws Exception {
        try {
        	byte[] hash1 = DigestUtils.sha256(novoVendedor.getSenha());
        	String senhaC = new String(hash1, StandardCharsets.UTF_8);
        	novoVendedor.setSenha(senhaC);
        	vendedorDAO.save(novoVendedor);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            inicializarUsuario();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

	public Vendedor getNovoVendedor() {
		return novoVendedor;
	}

	public void setNovoUsuario(Vendedor novoVendedor) {
		this.novoVendedor = novoVendedor;
	}

	public List<Vendedor> getListaVendedores() {
		if ( listaVendedores == null) {
			listaVendedores = vendedorDAO.findAllHQL();
		}
		return listaVendedores;
	}

	public void setListaVendedores(List<Vendedor> listaVendedores) {
		this.listaVendedores = listaVendedores;
	}
}
