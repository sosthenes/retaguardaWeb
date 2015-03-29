package br.com.retaguardaWeb.entidades;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import br.com.retaguardaWeb.util.Conversoes;

@Entity
@Table(name="quilometromotoboy")
public class QuilometroMotoBoy {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idQuilometroMotoBoy")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn(name="idMoto")
	private Moto moto;

	@ManyToOne
	@JoinColumn(name="idCaixa")
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data;
	
	private String kmInicial = "0";
	private String kmFinal = "0";
	private String valorGasolina = "0";
	@Transient
	private String valorPago = "0";
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getKmInicial() {
		return kmInicial;
	}
	public void setKmInicial(String kmInicial) {
		this.kmInicial = kmInicial;
	}
	public String getKmFinal() {
		return kmFinal;
	}
	public void setKmFinal(String kmFinal) {
		this.kmFinal = kmFinal;
	}
	public String getValorGasolina() {
		return valorGasolina;
	}
	public void setValorGasolina(String valorGasolina) {
		this.valorGasolina = valorGasolina;
	}
	
	@Transient
	public String getMyFormattedDate() {
		if (data != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(getData());
		} else {
			return "";
		}
	}
	public String getValorPago() {
		if(valorPago==null || valorPago.equals("0")){
			valorPago = gerarValor();
		}
		return valorPago;
	}
	
	private String gerarValor() {
		String valorPagamento = null;
		
		double inicial = 0;
		double finaL = 0;
		double valorGas = 0;
		double resultado = 0;
		
		inicial = Double.parseDouble(getKmInicial().replaceAll(",", "."));
		finaL = Double.parseDouble(getKmFinal().replaceAll(",", "."));
		valorGas = Double.parseDouble(getValorGasolina().replaceAll(",", "."));
		Conversoes conv = new Conversoes();
		resultado = (((finaL-inicial)/35)*valorGas);
		valorPagamento = conv.converteDoubleToString(resultado);
		return valorPagamento;
	}
	public void setValorPago(String valorPago) {
		this.valorPago = valorPago;
	}
	public Caixa getCaixa() {
		return caixa;
	}
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	
}
