package br.com.retaguardaWeb.vo;

import java.util.ArrayList;
import java.util.List;

import br.com.retaguardaWeb.entidades.Diariafuncionario;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.QuilometroMotoBoy;
import br.com.retaguardaWeb.entidades.Valefuncionario;

public class FechamentoPorCaixaVO {

	List<GastoCaixa> listaGastoCaixa = new ArrayList<GastoCaixa>();
	List<QuilometroMotoBoy> listaMotoBoyCaixa = new ArrayList<QuilometroMotoBoy>();
	List<Valefuncionario> listaValeCaixa = new ArrayList<Valefuncionario>();
	List<Diariafuncionario> listaDiariaCaixa = new ArrayList<Diariafuncionario>();
	
	
	private  String atendente;
	private String dinheiro;
	private String debito;
	private String credito;
	private String contaCliente;
	private String totalDeVendas;
	private String totalDinheiro;
	private String comandaRecebidaCancelada;
	
	
	
	
	
	public String getComandaRecebidaCancelada() {
		if(comandaRecebidaCancelada==null)
			comandaRecebidaCancelada="0";
		return comandaRecebidaCancelada;
	}
	public void setComandaRecebidaCancelada(String comandaRecebidaCancelada) {
		this.comandaRecebidaCancelada = comandaRecebidaCancelada;
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
	public List<GastoCaixa> getListaGastoCaixa() {
		return listaGastoCaixa;
	}
	public void setListaGastoCaixa(List<GastoCaixa> listaGastoCaixa) {
		this.listaGastoCaixa = listaGastoCaixa;
	}
	public List<QuilometroMotoBoy> getListaMotoBoyCaixa() {
		return listaMotoBoyCaixa;
	}
	public void setListaMotoBoyCaixa(List<QuilometroMotoBoy> listaMotoBoyCaixa) {
		this.listaMotoBoyCaixa = listaMotoBoyCaixa;
	}
	public List<Valefuncionario> getListaValeCaixa() {
		return listaValeCaixa;
	}
	public void setListaValeCaixa(List<Valefuncionario> listaValeCaixa) {
		this.listaValeCaixa = listaValeCaixa;
	}
	public String getTotalDeVendas() {
		return totalDeVendas;
	}
	public void setTotalDeVendas(String totalDeVendas) {
		this.totalDeVendas = totalDeVendas;
	}
	public String getTotalDinheiro() {
		return totalDinheiro;
	}
	public void setTotalDinheiro(String totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}
	public List<Diariafuncionario> getListaDiariaCaixa() {
		return listaDiariaCaixa;
	}
	public void setListaDiariaCaixa(List<Diariafuncionario> listaDiariaCaixa) {
		this.listaDiariaCaixa = listaDiariaCaixa;
	}
	
	
	
	
	
	
}
