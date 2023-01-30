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

import br.edu.HortifrutiProjeto.dao.ProductDAO;
import br.edu.HortifrutiProjeto.model.Product;

//@Model equivale a essas duas anotações
@RequestScoped
//Torna classe disponível na camada de visão (html)
@Named
public class ProductController {

	@Inject
	//Mensagens de erro para o usuário
    private FacesContext facesContext;

    @Inject
    private ProductDAO productDAO;

    private Product novoProduct;
    
    private List<Product> listaProducts;

    //Executa após instanciar classe UsuarioController, ou seja, 
    //a cada requisição (RequestScoped)
    @PostConstruct
    public void inicializarProduct() {
    	novoProduct = new Product();
    	listaProducts = productDAO.findAllHQL();
    }

    public void register() throws Exception {
        try {
//        	byte[] hash1 = DigestUtils.sha256(novoProduct.getSenha());
//        	String senhaC = new String(hash1, StandardCharsets.UTF_8);
//        	novoProduct.setSenha(senhaC);
        	productDAO.save(novoProduct);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            inicializarProduct();
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

	public Product getNovoProduct() {
		return novoProduct;
	}

	public void setNovoProduct(Product novoProduct) {
		this.novoProduct = novoProduct;
	}

	public List<Product> getListaProducts() {
		if (listaProducts == null) {
			listaProducts = productDAO.findAllHQL();
		}
		return listaProducts;
	}

	public void setListaProducts(List<Product> listaProducts) {
		this.listaProducts = listaProducts;
	}
}
