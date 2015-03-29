package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="loja")
public class Loja extends EntidadeBase{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6133539057839119236L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idLoja")
	private Long id;

	
	private String descricao;

	private String endereco;
	
	private String telefone;






	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	
	
	
}
