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

import br.com.retaguardaWeb.util.Conversoes;

@Entity
@Table(name="caixaperiodotrabalho")
public class CaixaPeriodoFuncionario extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -574675428607544751L;

	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idCaixaPeriodoTrabalho")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name="idCaixa")
	private Caixa idCaixa;
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja;
	
	@ManyToOne
	@JoinColumn(name="idPeriodoTrabalho")
	private PeriodoTrabalho periodoTrabalho;
	
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataHoraAbertura;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataHoraFechamento;
	
	private Double valorInicial;
	
	@Transient
	private Double valorTotalVendido;

	@OneToMany(mappedBy="caixaPeriodoTrabalho", cascade=CascadeType.ALL)
	private List<ValoresFechamentoCaixa> listaValoresFechamentoCaixa;

	
	public Double getValorInicial() {
		return valorInicial;
	}

	public String getValorInicialFormatado() {
		Conversoes conv = new Conversoes();
		return conv.converteDoubleToString(valorInicial);
	}

	
	
	public void setValorInicial(Double valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Caixa getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Caixa idCaixa) {
		this.idCaixa = idCaixa;
	}

	public PeriodoTrabalho getPeriodoTrabalho() {
		return periodoTrabalho;
	}

	public void setPeriodoTrabalho(PeriodoTrabalho periodoTrabalho) {
		this.periodoTrabalho = periodoTrabalho;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataHoraAbertura() {
		return dataHoraAbertura;
	}

	public String getDataHoraAberturaFormatado() {
		if(getDataHoraAbertura()!=null){
	        return new SimpleDateFormat("dd/MM/yyyy  HH:mm:ss").format(getDataHoraAbertura());
		}else{
			return "";
		}
	}

	public void setDataHoraAbertura(Date dataHoraAbertura) {
		this.dataHoraAbertura = dataHoraAbertura;
	}

	public Date getDataHoraFechamento() {
		return dataHoraFechamento;
	}

	public void setDataHoraFechamento(Date dataHoraFechamento) {
		this.dataHoraFechamento = dataHoraFechamento;
	}

	public Loja getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}

	public List<ValoresFechamentoCaixa> getListaValoresFechamentoCaixa() {
		return listaValoresFechamentoCaixa;
	}

	public void setListaValoresFechamentoCaixa(
			List<ValoresFechamentoCaixa> listaValoresFechamentoCaixa) {
		this.listaValoresFechamentoCaixa = listaValoresFechamentoCaixa;
	}

	public Double getValorTotalVendido() {
		return valorTotalVendido;
	}

	public String getValorTotalVendidoFormatado() {
		Conversoes c = new Conversoes();
		return c.converteDoubleToString(valorTotalVendido);
	}

	public void setValorTotalVendido(Double valorTotalVendido) {
		this.valorTotalVendido = valorTotalVendido;
	}
	
	

}
