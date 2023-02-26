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
import org.hibernate.validator.constraints.NotEmpty;

//Anotação que indica classe modelada pelo OCR - JPA (Hibernate)
@Entity
public class Product {

	//Chave primária da tabela
    @Id
    //Gerada automaticamente pelo banco
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Nome da coluna na tabela, necessário indicar quando atributo não tiver o mesmo nome
//    @Column(name = "id_product")
//    @NotNull
//    @NotEmpty
    private Integer id = 8;

    @NotEmpty
    @Pattern(regexp = "[^0-9]*")
    @Column(name = "nome")
    private String nome;
    
    @NotNull
    private Integer codigo;
    
    @NotNull
    private Integer quantitykl;
    
   
    @NotNull
    private Integer quantityint;
    
   
    @NotNull
    private Integer priceund;
    
    
    @NotNull
    private Integer pricekl;
    
    @NotNull
    private Integer quantidadeTotalemEstoque;
    
    @NotNull
    private Integer precoTotalemEstoque;
    
    //Indica mapeamento/relacionamento entre tabela
    //fetchType EAGER indica que atributo será carregado automaticamente, enquanto LAZY (padrão) indicaria carregamento sob demanda 
    //LAZY é mais eficiente pois economiza recursos computacionais mas é mais complexo de se trabalhar
    //cascadeType, MERGE indica que alterações serão transmitidas para elementos dependentes (tabelas relacionadas) automaticamente
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    //Mapeia tabela intermediária (criada em relacionamentos Muitos para muitos), não é necessário uma classe modelo para a tabela intermediária
    @JoinTable(
      name = "permissao",
      joinColumns = @JoinColumn(name = "id_product"),
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

	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getQuantitykl() {
		return quantitykl;
	}

	public void setQuantitykl(Integer quantitykl) {
		this.quantitykl = quantitykl;
	}

	public Integer getQuantityint() {
		return quantityint;
	}

	public void setQuantityint(Integer quantityint) {
		this.quantityint = quantityint;
	}

	public Integer getPriceund() {
		return priceund;
	}

	public void setPriceund(Integer priceund) {
		this.priceund = priceund;
	}

	public Integer getPricekl() {
		return pricekl;
	}

	public void setPricekl(Integer pricekl) {
		this.pricekl = pricekl;
	}

	public Integer getQuantidadeTotalemEstoque() {
		return quantidadeTotalemEstoque;
	}

	public void setQuantidadeTotalemEstoque(Integer quantidadeTotalemEstoque) {
		this.quantidadeTotalemEstoque = quantidadeTotalemEstoque;
	}

	public Integer getPrecoTotalemEstoque() {
		return precoTotalemEstoque;
	}

	public void setPrecoTotalemEstoque(Integer precoTotalemEstoque) {
		this.precoTotalemEstoque = precoTotalemEstoque;
	}

	public void setPermissoes(List<TipoPermissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}

}
