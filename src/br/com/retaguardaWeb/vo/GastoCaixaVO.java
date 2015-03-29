package br.com.retaguardaWeb.vo;

import java.util.Date;

import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;

public class GastoCaixaVO {

	private Loja loja;
	private String datagastoCaixa;
	private String datagastoCaixaFim;
	
	private GastoCaixa gastoCaixa;
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	public String getDatagastoCaixa() {
		return datagastoCaixa;
	}
	public void setDatagastoCaixa(String datagastoCaixa) {
		this.datagastoCaixa = datagastoCaixa;
	}
	public GastoCaixa getGastoCaixa() {
		return gastoCaixa;
	}
	public void setGastoCaixa(GastoCaixa gastoCaixa) {
		this.gastoCaixa = gastoCaixa;
	}
	public String getDatagastoCaixaFim() {
		return datagastoCaixaFim;
	}
	public void setDatagastoCaixaFim(String datagastoCaixaFim) {
		this.datagastoCaixaFim = datagastoCaixaFim;
	}
	
	
}
