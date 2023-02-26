package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

//Anotação que indica classe modelada pelo OCR - JPA (Hibernate)
@Entity
public class Usuario {

	//Chave primária da tabela
    @Id
    //Gerada automaticamente pelo banco
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Nome da coluna na tabela, necessário indicar quando atributo não tiver o mesmo nome
    @Column(name = "id_usuario")
    private Integer id;

    //Indica validações e mensagens de erro para atributo
    @NotNull
    @Size(min = 1, max = 25, message = "Mensagem customizada de erro! O nome do usuário deve ter no máximo 25 caracteres.")
    @Pattern(regexp = "[^0-9]*", message = "O nome de usuário não pode conter digitos.")
    @Column(unique = true)
    private String usuario;

    @NotNull
    @NotEmpty
    private String senha;
    
    @NotNull
    @NotEmpty
    @Email(message = "Não é um endereço de E-mail válido")
    private String email;
    
    @NotNull
    @NotEmpty
    private String cidade;
    
    @NotNull
    @NotEmpty
    private String pais;
    
    @NotNull
    @NotEmpty
    @CPF
    private String cpf;
    
    //Indica mapeamento/relacionamento entre tabela
    //fetchType EAGER indica que atributo será carregado automaticamente, enquanto LAZY (padrão) indicaria carregamento sob demanda 
    //LAZY é mais eficiente pois economiza recursos computacionais mas é mais complexo de se trabalhar
    //cascadeType, MERGE indica que alterações serão transmitidas para elementos dependentes (tabelas relacionadas) automaticamente
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    //Mapeia tabela intermediária (criada em relacionamentos Muitos para muitos), não é necessário uma classe modelo para a tabela intermediária
    @JoinTable(
      name = "permissao",
      joinColumns = @JoinColumn(name = "id_usuario"),
      inverseJoinColumns = @JoinColumn(name = "id_tipo_permissao")
    )
    private List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();

    //GETs e SETs são necessários para todos os atributos para que Hibernate funcione adequadamente
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}

}
