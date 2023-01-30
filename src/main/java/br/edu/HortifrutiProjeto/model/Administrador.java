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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "administrador")
public class Administrador {


    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sequencia")
    @SequenceGenerator(name="sequencia", sequenceName="usuario_seq", initialValue = 1, allocationSize = 1)
    
    private Integer id;

    
    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[^0-9]*")
    private String nome;
    
    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[^0-9]*")
    private String sobrenome;
    
    @NotEmpty
    @NotNull
    @Size(min = 8, max = 50)
    private String senha;
    
    @NotNull
    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;
    
    @NotNull
    @NotEmpty
    @CPF
    private String cpf;
    
    @NotNull
    @Size(min = 3, max = 30)
    @Pattern(regexp = "[^0-9]*")
    private String cargo;

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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
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

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	

}
