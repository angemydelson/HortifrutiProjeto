package br.edu.projeto.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Venda {
	//Chave primária da tabela
    @Id
    //Gerada automaticamente pelo banco
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Nome da coluna na tabela, necessário indicar quando atributo não tiver o mesmo nome
    @Column(name = "id_venda")
    private Integer id = 1;
    @NotEmpty
//    @Pattern(regexp = "[^0-9]*")
   
    private String codigoProduto;
    @NotNull
    private Integer quantidade;
    @NotNull
    private BigDecimal preco;
    @NotEmpty
 
  
    private String formaPagamento;
//    @NotEmpty
//    @Past(message = "A data da venda deve ser anterior à data atual")
//    @Size(max=50)
    private String dataVenda ;

    //Indica mapeamento/relacionamento entre tabela
    //fetchType EAGER indica que atributo será carregado automaticamente, enquanto LAZY (padrão) indicaria carregamento sob demanda 
    //LAZY é mais eficiente pois economiza recursos computacionais mas é mais complexo de se trabalhar
    //cascadeType, MERGE indica que alterações serão transmitidas para elementos dependentes (tabelas relacionadas) automaticamente
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    //Mapeia tabela intermediária (criada em relacionamentos Muitos para muitos), não é necessário uma classe modelo para a tabela intermediária
    @JoinTable(
      name = "permissao",
      joinColumns = @JoinColumn(name = "id_venda"),
      inverseJoinColumns = @JoinColumn(name = "id_tipo_permissao")
    )
    private List<TipoPermissao> permissoes = new ArrayList<TipoPermissao>();

   
    // getters e setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }
    
    public void setPermissoes(List<TipoPermissao> permissoes) {
		this.permissoes = permissoes;
	}

	public List<TipoPermissao> getPermissoes() {
		return permissoes;
	}
}
