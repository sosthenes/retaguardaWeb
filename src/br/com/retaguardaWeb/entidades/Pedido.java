package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


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
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="idPedido")
	@Fetch(FetchMode.SUBSELECT)
	private List<MesaPedido> mesas = new ArrayList<MesaPedido>();


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

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraPagamento;
	
	private boolean pago;

	private Double valorPago;
	
	private Double valorTroco;
	
	private boolean expedicao;
	
	@Transient
	private ExpedicaoPedido expedicaoPedido;
	
	
	public boolean isExpedicao() {
		return expedicao;
	}

	public void setExpedicao(boolean expedicao) {
		this.expedicao = expedicao;
	}

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

	public List<MesaPedido> getMesas() {
		
		return mesas;
	}

	public void setMesas(List<MesaPedido> mesas) {
		this.mesas = mesas;
	}

	public Pedido() {
		super();
		mesas = new ArrayList<MesaPedido>();
	}

	public Date getDataHoraPagamento() {
		return dataHoraPagamento;
	}

	public void setDataHoraPagamento(Date dataHoraPagamento) {
		this.dataHoraPagamento = dataHoraPagamento;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public ExpedicaoPedido getExpedicaoPedido() {
		return expedicaoPedido;
	}

	public void setExpedicaoPedido(ExpedicaoPedido expedicaoPedido) {
		this.expedicaoPedido = expedicaoPedido;
	}


	
	
}