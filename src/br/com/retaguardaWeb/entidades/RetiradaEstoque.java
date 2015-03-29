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
@Table(name="retiradaEstoque")
public class RetiradaEstoque {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idRetiradaEstoque")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idEstadoEstoque")
	private EstadoEstoque estadoEstoque;
	
	@ManyToOne
	@JoinColumn(name="idIngrediente")
	private Ingrediente idIngrediente = new Ingrediente();
	
	
	private String Quantidade;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public EstadoEstoque getEstadoEstoque() {
		return estadoEstoque;
	}


	public void setEstadoEstoque(EstadoEstoque estadoEstoque) {
		this.estadoEstoque = estadoEstoque;
	}


	public Ingrediente getIdIngrediente() {
		return idIngrediente;
	}


	public void setIdIngrediente(Ingrediente idIngrediente) {
		this.idIngrediente = idIngrediente;
	}


	public String getQuantidade() {
		return Quantidade;
	}


	public void setQuantidade(String quantidade) {
		Quantidade = quantidade;
	}
	
	
	
	
}
