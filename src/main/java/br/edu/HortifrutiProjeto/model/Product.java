package br.edu.HortifrutiProjeto.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name = "product")
public class Product{


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sequencias")
    @SequenceGenerator(name="sequencias", sequenceName="product_seq", initialValue = 1, allocationSize = 1)
    
    private Integer id;

    
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
    
    
	
}
