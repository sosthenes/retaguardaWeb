package br.com.retaguardaWeb.managedbeans;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.retaguardaWeb.entidades.Ingrediente;
import br.com.retaguardaWeb.entidades.UnidadeMedida;
import br.com.retaguardaWeb.services.IngredienteService;
import br.com.retaguardaWeb.services.UnidadeMedidaRepositorio;

@ManagedBean
public class IngredienteMB {

	@EJB
	private IngredienteService repositorio ;
	
	@EJB
	private UnidadeMedidaRepositorio unidadeMedidaService;
	
	 private Ingrediente ingrediente = new Ingrediente();
	 
	 private Long idUnidadeMedida;
	
	 private List <Ingrediente> ingredienteCache ;
	 private List<UnidadeMedida> listaUnidadeMedida;
	
	 public void adiciona () {
		 ingrediente.setIdUnidadeMedida(new UnidadeMedida());
		 ingrediente.getIdUnidadeMedida().setId(idUnidadeMedida);
		 this.repositorio.adiciona (this.ingrediente);
		 this.ingrediente = new Ingrediente ();
		 this.ingredienteCache = null ;
	 }
	
	 public List <Ingrediente> getIngredientes() {
		 if (this.ingredienteCache == null) {
			 this.ingredienteCache = this.repositorio.getIngredientes();
		 }
		 return this.ingredienteCache ;
	 }

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public List<Ingrediente> getIngredienteCache() {
		return ingredienteCache;
	}

	public void setIngredienteCache(List<Ingrediente> ingredienteCache) {
		this.ingredienteCache = ingredienteCache;
	}

	public List<UnidadeMedida> getListaUnidadeMedida() {
		 if (this.listaUnidadeMedida == null) {
			 this.listaUnidadeMedida = this.unidadeMedidaService.getIUnidadeMedidas();
		 }
		 return this.listaUnidadeMedida ;
	}

	public Long getIdUnidadeMedida() {
		return idUnidadeMedida;
	}

	public void setIdUnidadeMedida(Long idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}
	

	
}
