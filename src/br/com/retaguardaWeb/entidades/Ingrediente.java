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
@Table(name="ingrediente")
public class Ingrediente {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idIngrediente")
	private Long id;
	
	@Column(name="descricao")
	private String nome;
	
	
	@ManyToOne
	@JoinColumn(name = "idUnidadeMedida", referencedColumnName="idUnidadeMedida")
	private UnidadeMedida idUnidadeMedida;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public UnidadeMedida getIdUnidadeMedida() {
		return idUnidadeMedida;
	}


	public void setIdUnidadeMedida(UnidadeMedida idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}


	
	
	
}
