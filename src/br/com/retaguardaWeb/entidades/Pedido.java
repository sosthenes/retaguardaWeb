package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="pedido")
public class Pedido  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPedido")
	private Long id;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="idPedido")
	private List<PedidoProduto> pedidoProdutos = new ArrayList<PedidoProduto>();
	
	private double totalPedido;
	
	private String telefone;



	public List<PedidoProduto> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProduto> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public double getTotalPedido() {
		return totalPedido;
	}

	public void setTotalPedido(double totalPedido) {
		this.totalPedido = totalPedido;
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