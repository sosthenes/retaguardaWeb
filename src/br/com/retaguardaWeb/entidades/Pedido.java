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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


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
	
	
	private Long idCliente;

	@ManyToOne
	@JoinColumn(name="idEndereco")
	private Endereco enderecoEntrega;

	@ManyToOne
	@JoinColumn(name="idTipoPedido")
	private TipoVenda tipoPedido;

	@ManyToOne
	@JoinColumn(name="idFormaPagamento")
	private FormaPagamento formaPagamento;


	@ManyToOne
	@JoinColumn(name="caixaPeriodoFuncionario")
	private CaixaPeriodoFuncionario caixaPeriodoFuncionario;


	private Double valorPago;
	
	private Double valorTroco;
	
	public CaixaPeriodoFuncionario getCaixaPeriodoFuncionario() {
		return caixaPeriodoFuncionario;
	}

	public void setCaixaPeriodoFuncionario(
			CaixaPeriodoFuncionario caixaPeriodoFuncionario) {
		this.caixaPeriodoFuncionario = caixaPeriodoFuncionario;
	}


	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public TipoVenda getTipoPedido() {
		return tipoPedido;
	}

	public void setTipoPedido(TipoVenda tipoPedido) {
		this.tipoPedido = tipoPedido;
	}

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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(Double valorTroco) {
		this.valorTroco = valorTroco;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}


	
	
}