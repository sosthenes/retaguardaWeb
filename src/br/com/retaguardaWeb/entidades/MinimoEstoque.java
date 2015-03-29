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
@Table(name="estoqueminimoingrediente")
public class MinimoEstoque {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idEstoqueMinimoIngrediente")
	private Long idEstoqueMinimoIngrediente;
	
	@ManyToOne
	@JoinColumn(name="idIngrediente")
	private Ingrediente ingrediente;
	
	@ManyToOne
	@JoinColumn(name="idEstoque")
	private Estoque idEstoque;
	
	
	private String quantidade;


	public Long getIdEstoqueMinimoIngrediente() {
		return idEstoqueMinimoIngrediente;
	}


	public void setIdEstoqueMinimoIngrediente(Long idEstoqueMinimoIngrediente) {
		this.idEstoqueMinimoIngrediente = idEstoqueMinimoIngrediente;
	}


	public Ingrediente getIngrediente() {
		if(ingrediente==null)
			ingrediente=new Ingrediente();
		return ingrediente;
	}


	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}


	public Estoque getIdEstoque() {
		return idEstoque;
	}


	public void setIdEstoque(Estoque idEstoque) {
		this.idEstoque = idEstoque;
	}


	public String getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}


	public MinimoEstoque() {
		super();
	}
	
	
	
	
	
}
