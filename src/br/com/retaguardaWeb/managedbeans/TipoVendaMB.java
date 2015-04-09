package br.com.retaguardaWeb.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.TipoVenda;
import br.com.retaguardaWeb.sessionbeans.TipoVendaService;

@ManagedBean(name="tipoVendaMB")
@ViewScoped
public class TipoVendaMB extends BasicoMB{
	

	private TipoVenda tipoVenda;
	private List<TipoVenda> listaTipoVenda;
	@EJB
	private TipoVendaService tipoVendaService;
	
	private boolean expedicao;
	
	@PostConstruct
	public void init() {
		listar();
	}
	


	public boolean isExpedicao() {
		return expedicao;
	}



	public void setExpedicao(boolean expedicao) {
		this.expedicao = expedicao;
	}



	public TipoVenda getTipoVenda() {
		return tipoVenda;
	}

	public void setTipoVenda(TipoVenda tipoVenda) {
		this.tipoVenda = tipoVenda;
	}



	public List<TipoVenda> getListaTipoVenda() {
		return listaTipoVenda;
	}



	public void setListaTipoVenda(List<TipoVenda> listaTipoVenda) {
		this.listaTipoVenda = listaTipoVenda;
	}



	@Override
	public void adiciona() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void listar() {
		listaTipoVenda = tipoVendaService.getITipoVendas(getLoja());
		
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
