package br.com.retaguardaWeb.vo;

import br.com.retaguardaWeb.entidades.Diariafuncionario;

public class CaixaConsolidadoVO {

	private  String atendente;
	private String dinheiro;
	private String debito;
	private String credito;
	private String contaCliente;
	private  String vales;
	private String gastos;
	private String gasolina;
	private String resultado;
	
	private String totalDeVendas;
	private String totalDeGastos;
	
	
	
	
	
	
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public String getAtendente() {
		return atendente;
	}
	public void setAtendente(String atendente) {
		this.atendente = atendente;
	}
	public String getDinheiro() {
		if(dinheiro==null){
			dinheiro = "0";
		}
		return dinheiro;
	}
	public void setDinheiro(String dinheiro) {
		this.dinheiro = dinheiro;
	}
	public String getDebito() {
		if(debito==null){
			debito = "0";
		}
		return debito;
	}
	public void setDebito(String debito) {
		this.debito = debito;
	}
	public String getCredito() {
		if(credito==null){
			credito = "0";
		}
		return credito;
	}
	public void setCredito(String credito) {
		this.credito = credito;
	}
	public String getContaCliente() {
		if(contaCliente==null){
			contaCliente = "0";
		}
		return contaCliente;
	}
	public void setContaCliente(String contaCliente) {
		this.contaCliente = contaCliente;
	}
	public String getVales() {
		return vales;
	}
	public void setVales(String vales) {
		if(vales==null){
			vales = "0";
		}
		this.vales = vales;
	}
	public String getGastos() {
		return gastos;
	}
	public void setGastos(String gastos) {
		this.gastos = gastos;
	}
	public String getGasolina() {
		if(gasolina==null){
			gasolina = "0";
		}
		return gasolina;
	}
	public void setGasolina(String gasolina) {
		this.gasolina = gasolina;
	}
	public String getTotalDeVendas() {
		if(totalDeVendas==null){
			totalDeVendas = "0";
		}
		return totalDeVendas;
	}
	public void setTotalDeVendas(String totalDeVendas) {
		this.totalDeVendas = totalDeVendas;
	}
	public String getTotalDeGastos() {
		if(totalDeGastos==null){
			totalDeGastos = "0";
		}
		return totalDeGastos;
	}
	public void setTotalDeGastos(String totalDeGastos) {
		this.totalDeGastos = totalDeGastos;
	}

	
	
	
	
	
	
	
	
}
