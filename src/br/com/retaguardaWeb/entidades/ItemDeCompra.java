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
@Table(name="itemcompra")
public class ItemDeCompra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idItemCompra")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="idCompra")
	private Compras compra = new Compras();

	
	@ManyToOne
	@JoinColumn(name="idIngrediente")
	private Ingrediente idIngrediente = new Ingrediente();
	
	private String quantidade;
	
	private double valorUnitatio;
	
	private double valorTotal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Compras getCompra() {
		return compra;
	}

	public void setCompra(Compras compra) {
		this.compra = compra;
	}

	public Ingrediente getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Ingrediente idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public double getValorUnitatio() {
		return valorUnitatio;
	}

	public void setValorUnitatio(double valorUnitatio) {
		this.valorUnitatio = valorUnitatio;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
}
