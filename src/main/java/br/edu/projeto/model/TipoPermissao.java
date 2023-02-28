package br.edu.projeto.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.edu.projeto.util.Permissao;

@Entity
//Indica noem da tabela correspondente no banco, necessário somente quando nome da classe não é igual
@Table(name = "tipo_permissao")
public class TipoPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_permissao")
    private Integer id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Permissao permissao;
    
    //No relacionamento ManytoMany uma das classes deve ser "dominada" pela outra
    //A classe dominada possui o parâmetro mappedBy, essa classe nunca persistirá alterações na classe/tabela dominante
    @ManyToMany(mappedBy = "permissoes", fetch = FetchType.EAGER)
    private List<Usuario> usuarios = new ArrayList<Usuario>();
//    private List<Product> products = new ArrayList<Product>();
//    private List<Venda> vendas = new ArrayList<Venda>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Permissao getPermissao() {
		return permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
//	public List<Product> getProducts() {
//		return products;
//	}
//	public List<Venda> getVendas() {
//		return vendas;
//	}

	//Para persistir elementos de uma classe dominada é necessário adicionar o elementos de ambos os lados
	//Por isso é comum fazer um método especial para isso
	public void addUsuario(Usuario usuario) {
		this.usuarios.add(usuario);
		usuario.getPermissoes().add(this);
	}
	
//	public void addProduct(Product product) {
//		this.products.add(product);
//		product.getPermissoes().add(this);
//	}
//	public void addVenda(Venda venda) {
//		this.vendas.add(venda);
//		venda.getPermissoes().add(this);
//	}
}

