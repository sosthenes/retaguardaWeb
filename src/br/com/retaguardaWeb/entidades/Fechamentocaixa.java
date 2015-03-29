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
@Table(name="fechamentocaixa")
public class Fechamentocaixa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFechamentocaixa")
	private Long id;
	
	
	@ManyToOne
	@JoinColumn(name="idCaixa")
	private Caixa caixa;
	
	@Temporal(value = TemporalType.DATE)
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	
	private String dinheiro;
	private String debito;
	private String credito;
	private String contaCliente;
	private String comandaRecebidaCancelada;
	
	@Transient
	private String gasolina;
	@Transient
	private String gasto;
	@Transient
	private String vale;
	
	@Transient
	private String totalVenda;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Caixa getCaixa() {
		return caixa;
	}
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
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
	public String getDinheiro() {
		if(dinheiro==null || dinheiro.equals("")){
			dinheiro = "0";
		}
		return trataValores(dinheiro);
	}
	public void setDinheiro(String dinheiro) {
		this.dinheiro = dinheiro;
	}
	public String getDebito() {
		if(debito==null || debito.equals("")){
			debito = "0";
		}
		return trataValores(debito);
	}
	public void setDebito(String debito) {
		this.debito = debito;
	}
	public String getCredito() {
		if(credito==null || credito.equals("")){
			credito = "0";
		}
		return trataValores(credito);
	}
	public void setCredito(String credito) {
		this.credito = credito;
	}
	public String getContaCliente() {
		if(contaCliente==null || contaCliente.equals("")){
			contaCliente = "0";
		}
		return trataValores(contaCliente);
	}
	public void setContaCliente(String contaCliente) {
		this.contaCliente = contaCliente;
	}
	public String getComandaRecebidaCancelada() {
		return  trataValores(comandaRecebidaCancelada);
	}
	public void setComandaRecebidaCancelada(String comandaRecebidaCancelada) {
		this.comandaRecebidaCancelada = comandaRecebidaCancelada;
	}
	
	@Transient
	public String getMyFormattedDate() {
		if (data != null) {
			return new SimpleDateFormat("dd/MM/yyyy").format(getData());
		} else {
			return "";
		}
	}

	public String getTotalVenda() {
		double total = 0.00;
			total+=Double.parseDouble(getDinheiro().replaceAll(",", "."));
			total+=Double.parseDouble(getDebito().replaceAll(",", "."));
			total+=Double.parseDouble(getCredito().replaceAll(",", "."));
			total+=Double.parseDouble(getContaCliente().replaceAll(",", "."));
			totalVenda = new Conversoes().converteDoubleToString(total);
		return totalVenda;
	}
	public String getGasolina() {
		return gasolina;
	}
	public void setGasolina(String gasolina) {
		this.gasolina = gasolina;
	}
	public String getGasto() {
		return gasto;
	}
	public void setGasto(String gasto) {
		this.gasto = gasto;
	}
	public String getVale() {
		return vale;
	}
	public void setVale(String vale) {
		this.vale = vale;
	}


	public String trataValores(String campo){
		if(campo!=null && !campo.equals("0")){
		campo.replaceAll(".", "");
		campo.replaceAll(",", ".");
		}
		return campo;
	}
	
	
}
