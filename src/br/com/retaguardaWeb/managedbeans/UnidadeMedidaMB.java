package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.retaguardaWeb.entidades.UnidadeMedida;
import br.com.retaguardaWeb.services.UnidadeMedidaRepositorio;

@ManagedBean
public class UnidadeMedidaMB {

	@EJB
	private UnidadeMedidaRepositorio unidadeMedidaService;
	
	private java.util.List<UnidadeMedida> unidades  = new ArrayList<UnidadeMedida>();
	
	private UnidadeMedida unidadeMedida = new UnidadeMedida();
	
	public void adiciona () {
		if(unidadeMedida!=null){
			this.unidadeMedidaService.adiciona (this.unidadeMedida);
		 this.unidadeMedida= new UnidadeMedida();
		}else{
			unidadeMedida = new UnidadeMedida();
		}
	 }
	 
	 
	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}


	public java.util.List<UnidadeMedida> getUnidades() {
		return unidadeMedidaService.getIUnidadeMedidas();
	}


	public void setUnidades(java.util.List<UnidadeMedida> unidades) {
		this.unidades = unidades;
	}
	 
	 
}
