package br.com.retaguardaWeb.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.TipoGasto;
import br.com.retaguardaWeb.entidades.UnidadeMedida;
import br.com.retaguardaWeb.services.TipoGastoService;
import br.com.retaguardaWeb.services.UnidadeMedidaRepositorio;

@ManagedBean
@ViewScoped
public class TipoGastoMB extends BasicoMB{

	@EJB
	private TipoGastoService repositorio ;
	
	
	 private TipoGasto tipoGasto;
	 
	
	 private List <TipoGasto> tipoGastoCache ;
	
	 public void adiciona () {
		 tipoGasto.setIdLoja(getLoja());
		 this.repositorio.adiciona (this.tipoGasto);
		 this.tipoGasto = new TipoGasto ();
		 this.tipoGastoCache = null ;
		 retornaMensagemSucessoOperacao();
		 limpar();
	 }
	
	 public List <TipoGasto> getTipoGastos() {
		 if (this.tipoGastoCache == null) {
			 this.tipoGastoCache = this.repositorio.getTipoGastos(getLoja());
		 }
		 return this.tipoGastoCache ;
	 }

	 
		public void listar() {
			 this.tipoGastoCache = this.repositorio.getTipoGastos(getLoja());
			 getTipoGastos();
		}
	 	 
	 
		public void remover() {
			repositorio.remover(getTipoGasto());
			listar();
		}
	 
	 
	public TipoGasto getTipoGasto() {
		if(tipoGasto==null)
			tipoGasto = new TipoGasto();
		return tipoGasto;
	}

	public void setTipoGasto(TipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public List<TipoGasto> getTipoGastoCache() {
		return tipoGastoCache;
	}

	public void setTipoGastoCache(List<TipoGasto> tipoGastoCache) {
		this.tipoGastoCache = tipoGastoCache;
	}

	@Override
	public void limpar() {
		tipoGasto = new TipoGasto();
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	

	
}
