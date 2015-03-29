package br.com.retaguardaWeb.entidades;

import java.text.SimpleDateFormat;
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

@Entity
@Table(name="pagamento")
public class Pagamento  extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4799336851112193182L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPagamento")
	private Long id;

	
	private String descricao;

	private double valor;
	private double valorPago;
	private Integer quantidade;
	private Integer diaPgto;
	
	private boolean pago;
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataCompra;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataPagamento;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataFim;
	
	@ManyToOne
	@JoinColumn(name="iddescricaogasto")
	private DescricaoPagamento descricaoPagamento = new DescricaoPagamento();
	
	@ManyToOne
	@JoinColumn(name="idTipoPagamento")
	private TipoDePagamento tipoPagamento = new TipoDePagamento();
	
	@ManyToOne
	@JoinColumn(name="idFormaPagamento")
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(name="idPeriodicidade")
	private Periodicidade periodocidade = new Periodicidade();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="pagamento")
	private List<ParcelaPagamento> parcelas;
	
	
	
	
	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


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


	public Pagamento() {
		super();
	}


	public double getValor() {
		return valor;
	}


	public void setValor(double valor) {
		this.valor = valor;
	}


	public double getValorPago() {
		return valorPago;
	}


	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}


	public boolean isPago() {
		return pago;
	}


	public void setPago(boolean pago) {
		this.pago = pago;
	}


	public Date getDataCompra() {
		return dataCompra;
	}


	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}


	public Date getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}


	public TipoDePagamento getTipoPagamento() {
		return tipoPagamento;
	}


	public void setTipoPagamento(TipoDePagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}


	public Periodicidade getPeriodocidade() {
		return periodocidade;
	}


	public void setPeriodocidade(Periodicidade periodocidade) {
		this.periodocidade = periodocidade;
	}


	public Integer getQuantidade() {
		return quantidade;
	}


	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}


	public Integer getDiaPgto() {
		return diaPgto;
	}


	public void setDiaPgto(Integer diaPgto) {
		this.diaPgto = diaPgto;
	}

	@Transient
	 public String getMyFormattedDate() {
		if(dataCompra!=null){
	        return new SimpleDateFormat("dd/MM/yyyy").format(getDataCompra());
		}else{
			return "";
		}
	    }


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<ParcelaPagamento> getParcelas() {
		return parcelas;
	}


	public void setParcelas(List<ParcelaPagamento> parcelas) {
		this.parcelas = parcelas;
	}


	public DescricaoPagamento getDescricaoPagamento() {
		return descricaoPagamento;
	}


	public void setDescricaoPagamento(DescricaoPagamento descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}


	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}


	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	
	
	
}
