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
@Table(name="estoqueatual")
public class EstoqueAtual  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idEstoqueatual")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idIngrediente")
	private Ingrediente idIngrediente = new Ingrediente();
	
	@ManyToOne
	@JoinColumn(name="idEstadoEstoque")
	private EstadoEstoque estadoEstoque;

	
	private String quantidade;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingrediente getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Ingrediente idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public EstadoEstoque getEstadoEstoque() {
		if(estadoEstoque==null)
			estadoEstoque = new EstadoEstoque();
		return estadoEstoque;
	}

	public void setEstadoEstoque(EstadoEstoque estadoEstoque) {
		this.estadoEstoque = estadoEstoque;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}


	
	
}