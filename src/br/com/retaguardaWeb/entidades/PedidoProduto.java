package br.com.retaguardaWeb.entidades;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name="pedidoproduto")
public class PedidoProduto  {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idpedidoProduto")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idPedido")
	private Pedido idPedido;
	
	@ManyToOne
	@JoinColumn(name="idProduto")
	private Produto produtos;
	
	@Transient
	private Produto produtosMeia;
	
	
	private boolean meia;
	

	public Produto getProdutos() {
		return produtos;
	}

	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Pedido idPedido) {
		this.idPedido = idPedido;
	}

	public boolean isMeia() {
		return meia;
	}

	public void setMeia(boolean meia) {
		this.meia = meia;
	}

	public Produto getProdutosMeia() {
		return produtosMeia;
	}

	public void setProdutosMeia(Produto produtosMeia) {
		this.produtosMeia = produtosMeia;
	}


	
	
}