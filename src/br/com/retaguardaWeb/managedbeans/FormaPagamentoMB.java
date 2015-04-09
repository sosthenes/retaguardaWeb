package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.services.FormaPagamentoService;

@Named
@ViewScoped
public class FormaPagamentoMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;
	

	private FormaPagamento formaPagamento;
	private List<FormaPagamento> listaFormaPagamento;
	@EJB
	private FormaPagamentoService formaPagamentoService;
	
	@PostConstruct
	public void init() {
		listar();
	}
	


	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}



	public List<FormaPagamento> getListaFormaPagamento() {
		return listaFormaPagamento;
	}



	public void setListaFormaPagamento(List<FormaPagamento> listaFormaPagamento) {
		this.listaFormaPagamento = listaFormaPagamento;
	}



	@Override
	public void adiciona() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void listar() {
		listaFormaPagamento = formaPagamentoService.listaFormaPagamento();
		
	}



	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
