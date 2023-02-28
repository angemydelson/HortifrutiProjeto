package br.edu.projeto.model;

import java.util.ArrayList;
import java.math.BigDecimal;
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
    @Column(name = "id_product")
    private Integer id = 1;

    @NotEmpty
    @Pattern(regexp = "[^0-9]*")
    @Column(name = "nome")
    private String nome;
    
    @NotNull
    private Integer codigo;
    
    @NotNull
    private Double quantitykl;
    
   
    @NotNull
    private Double quantityint;
    
   
    @NotNull
    private Double priceund;
    
    
    @NotNull
    private Double pricekl;
    
    @NotNull
    private Double quantidadeTotalemEstoque;
    
    @NotNull
    private Double precoTotalemEstoque;
    
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

	public Double getQuantitykl() {
		return quantitykl;
	}

	public void setQuantitykl(Double quantitykl) {
		this.quantitykl = quantitykl;
	}

	public Double getQuantityint() {
		return quantityint;
	}

	public void setQuantityint(Double quantityint) {
		this.quantityint = quantityint;
	}

	public Double getPriceund() {
		return priceund;
	}

	public void setPriceund(Double priceund) {
		this.priceund = priceund;
	}

	public Double getPricekl() {
		return pricekl;
	}

	public void setPricekl(Double pricekl) {
		this.pricekl = pricekl;
	}

	public Double getQuantidadeTotalemEstoque() {
		return quantidadeTotalemEstoque;
	}

	public void setQuantidadeTotalemEstoque(Double quantidadeTotalemEstoque) {
		this.quantidadeTotalemEstoque = quantidadeTotalemEstoque;
	}

	public Double getPrecoTotalemEstoque() {
		return precoTotalemEstoque;
	}

	public void setPrecoTotalemEstoque(Double precoTotalemEstoque) {
		this.precoTotalemEstoque = precoTotalemEstoque;
	}

	public void setPermissoes(List<TipoPermissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}

}
