package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="fornecedor")
public class Fornecedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFornecedor")
	private Long id;

	
	private String descricao;
	
	private String endereco;
	
	private String telefone;

	private String celular;
	
	private String nomeContato1;
	private String telefoneContato1;
	
	private String nomeContato2;
	private String telefoneContato2;


	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public Fornecedor() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


	public String getEndereco() {
		return endereco;
	}


	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}


	public String getCelular() {
		return celular;
	}


	public void setCelular(String celular) {
		this.celular = celular;
	}


	public String getNomeContato1() {
		return nomeContato1;
	}


	public void setNomeContato1(String nomeContato1) {
		this.nomeContato1 = nomeContato1;
	}


	public String getTelefoneContato1() {
		return telefoneContato1;
	}


	public void setTelefoneContato1(String telefoneContato1) {
		this.telefoneContato1 = telefoneContato1;
	}


	public String getNomeContato2() {
		return nomeContato2;
	}


	public void setNomeContato2(String nomeContato2) {
		this.nomeContato2 = nomeContato2;
	}


	public String getTelefoneContato2() {
		return telefoneContato2;
	}


	public void setTelefoneContato2(String telefoneContato2) {
		this.telefoneContato2 = telefoneContato2;
	}



	
	
}
