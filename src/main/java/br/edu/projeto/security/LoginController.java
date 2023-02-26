package br.edu.projeto.security;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.Principal;
import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;


import br.edu.projeto.model.Usuario;

//Controlador da página de Login
@Named 
@RequestScoped
public class LoginController {

	@Inject
    private FacesContext facesContext;

    @Inject
    private SecurityContext securityContext;

    private Usuario usuario;

    @PostConstruct
    public void inicializarUsuario() {
        usuario = new Usuario();
    }

    public void login() throws IOException {
        if (facesContext.getExternalContext().getAuthType() != null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existe um usuário autenticado! Use a opção logout primeiro.", ""));
            }
        } else {
            Credential credential = new UsernamePasswordCredential(usuario.getUsuario(), new Password(usuario.getSenha()));
            AuthenticationStatus status = securityContext.authenticate(
                    (HttpServletRequest) facesContext.getExternalContext().getRequest(),
                    (HttpServletResponse) facesContext.getExternalContext().getResponse(),
                    AuthenticationParameters.withParams().credential(credential));
            if (status.equals(AuthenticationStatus.SUCCESS)) {
//            	facesContext.getExternalContext().redirect("cadastro_usuario.xhtml");
//            	facesContext.getExternalContext().redirect("cadastro_product.xhtml");
//                if (!this.facesContext.getExternalContext().isUserInRole("ADMINISTRADOR")) {
//                    facesContext.getExternalContext().redirect("cadastro_usuario.xhtml");
//                } else {
//                    facesContext.getExternalContext().redirect("cadastro_product.xhtml");
//                }
            	if (facesContext.getExternalContext().isUserInRole("ADMINISTRADOR")) {
            	    facesContext.getExternalContext().redirect("cadastro_usuario.xhtml");
            	} else {
            	    facesContext.getExternalContext().redirect("cadastro_product.xhtml");
            	}

            } else if (status.equals(AuthenticationStatus.SEND_FAILURE)) {
                usuario = new Usuario();
                try {
                    throw new Exception();
                } catch (Exception e) {
                    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Inválido!", "Usuário ou senha incorretos."));
                }
            }
        }
    }

    
    public void logout() throws IOException {
    	facesContext.getExternalContext().invalidateSession();
    	facesContext.getExternalContext().redirect("logout.xhtml");
    }
    
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
