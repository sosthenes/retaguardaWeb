package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Compras;
import br.com.retaguardaWeb.entidades.Fornecedor;
import br.com.retaguardaWeb.entidades.Ingrediente;
import br.com.retaguardaWeb.entidades.ItemDeCompra;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.sessionbeans.FornecedorService;
import br.com.retaguardaWeb.sessionbeans.IngredienteService;

@ManagedBean
@SessionScoped
public class ComprasMB {

	@EJB
	private IngredienteService repositorioIngrediente ;
	
	@EJB
	private FornecedorService repositorioFornecedor ;
	
	
	private Compras compra = new Compras();
	private ItemDeCompra item = new ItemDeCompra();
	private List<ItemDeCompra> listaItem = new ArrayList<ItemDeCompra>();
	private Loja loja = new Loja();
	private Long idLoja;
	private List <Ingrediente> ingredienteCache ;
	private List <Fornecedor> fornecedoresCache ;
	private Long idIngrediente;
	
	public void adicionaItem(){
		Ingrediente ingred = new Ingrediente();
		ingred.setId(getIdIngrediente());
		ingred = repositorioIngrediente.ingredientePorId(ingred);
		getItem().setIdIngrediente(ingred);
		listaItem.add(getItem());
	}
	
	public List <Ingrediente> getIngredientes() {
		 if (this.ingredienteCache == null) {
			 this.ingredienteCache = this.repositorioIngrediente.getIngredientes();
		 }
		 return this.ingredienteCache ;
	 }

	public List <Fornecedor> getFornecedores() {
		 if (this.fornecedoresCache == null) {
			 this.fornecedoresCache = this.repositorioFornecedor.getFornecedores();
		 }
		 return this.fornecedoresCache ;
	 }



	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Compras getCompra() {
		return compra;
	}

	public void setCompra(Compras compra) {
		this.compra = compra;
	}

	public Long getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Long idLoja) {
		this.idLoja = idLoja;
	}

	public ItemDeCompra getItem() {
		return item;
	}

	public void setItem(ItemDeCompra item) {
		this.item = item;
	}

	public List<Ingrediente> getIngredienteCache() {
		return ingredienteCache;
	}

	public void setIngredienteCache(List<Ingrediente> ingredienteCache) {
		this.ingredienteCache = ingredienteCache;
	}

	public List<Fornecedor> getFornecedoresCache() {
		return fornecedoresCache;
	}

	public void setFornecedoresCache(List<Fornecedor> fornecedoresCache) {
		this.fornecedoresCache = fornecedoresCache;
	}

	public Long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public List<ItemDeCompra> getListaItem() {
		return listaItem;
	}

	public void setListaItem(List<ItemDeCompra> listaItem) {
		this.listaItem = listaItem;
	}
	
	
	
	
	
	
}
