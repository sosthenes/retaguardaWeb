package br.com.retaguardaWeb.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="parcelas")
public class ParcelaPagamento extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1428065267001755393L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idParcelas")
	private Long id;

	
	private String descricao;

	private Integer numParcela;
	private Double valorParcela;
	private Double valorPago;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataParcela;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataPagamento;
	
	
	private boolean pago;
	
	
	@ManyToOne
	@JoinColumn(name="idPagamento")
	private Pagamento pagamento;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public ParcelaPagamento() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getNumParcela() {
		return numParcela;
	}


	public void setNumParcela(Integer numParcela) {
		this.numParcela = numParcela;
	}


	public Double getValorParcela() {
		return valorParcela;
	}


	public void setValorParcela(Double valorParcela) {
		this.valorParcela = valorParcela;
	}


	public Double getValorPago() {
		return valorPago;
	}


	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}


	public Date getDataParcela() {
		return dataParcela;
	}


	public void setDataParcela(Date dataParcela) {
		this.dataParcela = dataParcela;
	}


	public Date getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


	public Pagamento getPagamento() {
		return pagamento;
	}


	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}


	public boolean isPago() {
		return pago;
	}


	public void setPago(boolean pago) {
		this.pago = pago;
	}

	@Transient
	public String getMyFormattedDate() {
		if (dataParcela != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataParcela);
		} else {
			return "";
		}
	}
	
	@Transient
	public String getMyFormattedDataPagamento() {
		if (dataPagamento != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataPagamento);
		} else {
			return "";
		}
	}
	
	
	
}
