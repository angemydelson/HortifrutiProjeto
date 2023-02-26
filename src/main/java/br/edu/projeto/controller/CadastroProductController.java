package br.edu.projeto.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import org.primefaces.PrimeFaces;

import br.edu.projeto.dao.TipoPermissaoDAO;
import br.edu.projeto.dao.ProductDAO;
import br.edu.projeto.model.TipoPermissao;
import br.edu.projeto.model.Product;

//Escopo do objeto da classe (Bean)
//ApplicationScoped é usado quando o objeto é único na aplicação (compartilhado entre usuários), permanece ativo enquanto a aplicação estiver ativa
//SessionScoped é usado quando o objeto é único por usuário, permanece ativo enquanto a sessão for ativa
//ViewScoped é usado quando o objeto permanece ativo enquanto não houver um redirect (acesso a nova página)
//RequestScoped é usado quando o objeto só existe naquela requisição específica
//Quanto maior o escopo, maior o uso de memória no lado do servidor (objeto permanece ativo por mais tempo)
//Escopos maiores que Request exigem que classes sejam serializáveis assim como todos os seus atributos (recurso de segurança)
@ViewScoped
//Torna classe disponível na camada de visão (html) - são chamados de Beans ou ManagedBeans (gerenciados pelo JSF/EJB)
@Named
public class CadastroProductController implements Serializable {

	//Anotação que marca atributo para ser gerenciado pelo CDI
	//O CDI criará uma instância do objeto automaticamente quando necessário
	@Inject
	private FacesContext facesContext;
	
	//atributos que não podem ser serializáveis (normalmente dependências externas) devem ser marcados como transient 
	//(eles são novamente criados a cada nova requisição independente do escopo da classe)
//	@Inject
//    transient private Pbkdf2PasswordHash passwordHash;
	
	@Inject
    private ProductDAO productDAO;
	
	@Inject
    private TipoPermissaoDAO tipoPermissaoDAO;
	
	private Product product;
	
	private List<Product> listaProducts;
	
	private List<SelectItem> permissoes;
	
	private List<Integer> permissoesSelecionadas;
	
	private String termoDePesquisa;
	
	//Anotação que força execução do método após o construtor da classe ser executado
    @PostConstruct
    public void init() {
    	//Verifica se usuário está autenticado e possui a permissão adequada
    	if (!this.facesContext.getExternalContext().isUserInRole("CLIENTE")) {
    		try {
				this.facesContext.getExternalContext().redirect("login-error.xhtml");
			} catch (IOException e) {e.printStackTrace();}
    	}
    	//Inicializa elementos importantes
    	this.permissoesSelecionadas = new ArrayList<Integer>();
    	this.listaProducts = productDAO.listarTodos();
//    	//O elemento de checkbox da tela usa uma lista do tipo SelectItem
//    	this.permissoes = new ArrayList<SelectItem>();
//    	//É necessário mapear a lista de permissões manualmente para o tipo SelectItem
//    	for (TipoPermissao p: this.tipoPermissaoDAO.listarTodos()) {
//    		//O primeiro elemento é a chave (oculta) e o segundo a descrição que aparecerá para o usuário em tela
//    		SelectItem i = new SelectItem(p.getPermissao().id, p.getPermissao().name());		
//    		this.permissoes.add(i);
//    	}
    }
	
    //Chamado pelo botão novo
	public void novoCadastro() {
        this.setProduct(new Product());
    }
	
	//Chamado ao salvar cadastro de usuário (novo ou edição)
	public void salvar() {
		//Chama método de verificação se usuário é válido (regras negociais)
//        if (usuarioValido()) {
//        	//Limpa lista de permissões de usuário (é mais simples limpar e adicionar todas novamente depois)
//    		this.product.getPermissoes().clear();
//    		//Adiciona todas as permissões selecionadas em tela
//    		for (Integer id: this.permissoesSelecionadas) {
//    			TipoPermissao permissao = tipoPermissaoDAO.encontrarPermissao(id);
//    			//Chama método que adiciona o usuário para a permissão e vice-versa (necessário em relacionamento ManyToMany)
////    			permissao.addProduct(this.product);	
//    			
//    		}
//        	try {
        		//Aplica Hash na senha
//        		this.product.setSenha(this.passwordHash.generate(this.product.getSenha().toCharArray()));
		        //Verifica se é um novo cadastro ou é a alteração de um cadastro
        		if (this.product.getId() == null) {
		        	this.productDAO.salvar(this.product);
		        	this.facesContext.addMessage(null, new FacesMessage("Produto Criado"));
		        } else {
		        	this.productDAO.atualizar(this.product);
		        	this.facesContext.addMessage(null, new FacesMessage("Produto Atualizado"));
		        }
        		//Após salvar usuário é necessário recarregar lista que popula tabela com os novos dados
		        this.listaProducts = productDAO.listarTodos();
		        //Atualiza e executa elementos Javascript na tela assincronamente
			    PrimeFaces.current().executeScript("PF('productDialog').hide()");
			    PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
//	        } catch (Exception e) {
//	            String errorMessage = getMensagemErro(e);
//	            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
//	        }
//        }
	}	
	
	public void listar() {
//	    listaProducts = productDAO.buscarPorTermo(termoDePesquisa);
	  //Após salvar usuário é necessário recarregar lista que popula tabela com os novos dados
        this.listaProducts = productDAO.buscarPorTermo(termoDePesquisa);
        //Atualiza e executa elementos Javascript na tela assincronamente
	    PrimeFaces.current().executeScript("PF('productDialog').hide()");
	    PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
	}
	
	//Realiza validações adicionais (não relizadas no modelo) e/ou complexas/interdependentes
	private boolean usuarioValido() {
//		if (this.permissoesSelecionadas.isEmpty()) {
//			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Selecione ao menos uma permissão para o novo usuário.", null));
//			return false;
//		}			
		if (this.product.getId() == null && !this.productDAO.ehProductUnico(this.product.getNome())) {
			this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Este nome de produto já está em uso.", null));
			return false;
		}
		return true;
	}
	
	//Chamado pelo botão remover da tabela
	public void remover() {
		try {
			this.productDAO.excluir(this.product);
			//Após excluir usuário é necessário recarregar lista que popula tabela com os novos dados
			this.listaProducts = productDAO.listarTodos();
	        //Limpa seleção de usuário
			this.product = null;
	        this.facesContext.addMessage(null, new FacesMessage("Produto Removido"));
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-products");
        } catch (Exception e) {
            String errorMessage = getMensagemErro(e);
            this.facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, null));
        }
	}
	
	//Chamado pelo botão alterar da tabela
	public void alterar() {
		this.permissoesSelecionadas.clear();
		for (TipoPermissao p: this.product.getPermissoes())
			this.permissoesSelecionadas.add(p.getId());
//		this.product.setSenha("");
	}
	
	
	
	//Captura mensagem de erro das validações do Hibernate
	private String getMensagemErro(Exception e) {
        String erro = "Falha no sistema!. Contacte o administrador do sistema.";
        if (e == null) 
            return erro;
        Throwable t = e;
        while (t != null) {
            erro = t.getLocalizedMessage();
            t = t.getCause();
        }
        return erro;
    }
	
	//GETs e SETs
	//GETs são necessários para elementos visíveis em tela
	//SETs são necessários para elementos que serão editados em tela
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Product> getListaProducts() {
		return listaProducts;
	}

	public void setListaProducts(List<Product> listaProducts) {
		this.listaProducts = listaProducts;
	}

	public List<SelectItem> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<SelectItem> permissoes) {
		this.permissoes = permissoes;
	}

	public List<Integer> getPermissoesSelecionadas() {
		return permissoesSelecionadas;
	}

	public void setPermissoesSelecionadas(List<Integer> permissoesSelecionadas) {
		this.permissoesSelecionadas = permissoesSelecionadas;
	}
	
	public String getTermoDePesquisa() {
	    return termoDePesquisa;
	}

	public void setTermoDePesquisa(String termoDePesquisa) {
	    this.termoDePesquisa = termoDePesquisa;
	}

}

