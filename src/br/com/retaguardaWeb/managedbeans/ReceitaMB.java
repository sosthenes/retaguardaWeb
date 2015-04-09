package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import br.com.retaguardaWeb.entidades.Ingrediente;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.ReceitaProduto;
import br.com.retaguardaWeb.entidades.UnidadeMedida;
import br.com.retaguardaWeb.services.IngredienteService;
import br.com.retaguardaWeb.services.ProdutoService;
import br.com.retaguardaWeb.services.ReceitasServices;
import br.com.retaguardaWeb.services.UnidadeMedidaRepositorio;

@ManagedBean
@ViewScoped
public class ReceitaMB {

	@EJB
	private ReceitasServices receitaService;
	@EJB
	private UnidadeMedidaRepositorio unidadeMedidaService;
	@EJB
	private IngredienteService repositorio ;
	@EJB
	private ProdutoService produtoService;
	private Produto produto = new Produto();
	private ReceitaProduto receita = new ReceitaProduto();
	
	private Long idProduto;
	private Long idUnidadeMedida;
	private Long idIngrediente;

	private List <Produto> listaProduto;
	private List <Ingrediente> ingredienteCache ;
	private List<UnidadeMedida> listaUnidadeMedida;
	
	private List<ReceitaProduto>listaReceitas = new ArrayList<ReceitaProduto>();
	
	public void adiciona () {
		populaReceita();
		if(produto!=null){
			this.receitaService.adiciona (this.receita);
			listaReceitas();
			produto = new Produto();
			idIngrediente = 0L;
			idProduto = 0L;
			idUnidadeMedida= 0L;
			receita = new ReceitaProduto();
		}else{
			produto = new Produto();
		}
	 }
	
	public void populaReceita(){
		produto.setId(getIdProduto());
		produto = produtoService.pesquisa(produto);
		this.receita.setIdProduto(produto);
		this.receita.getIdIngrediente().setId(idIngrediente);
		receita.getQuantidade();
		this.receita.getIdUnidadeMedida().setId(idUnidadeMedida);
		
	}
	
	
	public void pegaValor(ReceitaProduto r) {
		setReceita(r);
	}
	
	public void excluir() {
		receitaService.remover(getReceita());
		listaReceitas();
		receita = new ReceitaProduto();
	}
	 
	public List<Produto> getListaProduto() {
		 if (this.listaProduto == null) {
			 this.listaProduto =  this.produtoService.getProdutos();
		 }
		return listaProduto;
	}
	 
	public List<UnidadeMedida> getListaUnidadeMedida() {
		 if (this.listaUnidadeMedida == null ) {
			 this.listaUnidadeMedida = this.unidadeMedidaService.getIUnidadeMedidas();
		 }
		 return this.listaUnidadeMedida ;
	}

	public List <Ingrediente> getIngredientes() {
		 if (this.ingredienteCache == null) {
			 this.ingredienteCache = this.repositorio.getIngredientes();
		 }
		 return this.ingredienteCache ;
	 }
	
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public java.util.List<ReceitaProduto> getReceitas() {
		return receitaService.getReceitaProdutos();
	}





	public ReceitaProduto getReceita() {
		return receita;
	}

	public void setReceita(ReceitaProduto receita) {
		this.receita = receita;
	}

	public Long getIdProduto() {
		return idProduto;
	}


	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}


	public Long getIdUnidadeMedida() {
		return idUnidadeMedida;
	}


	public void setIdUnidadeMedida(Long idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}


	public Long getIdIngrediente() {
		return idIngrediente;
	}


	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}




	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<ReceitaProduto> listaReceitas() {
		if(idProduto!=null){
			produto.setId(idProduto);
			listaReceitas = receitaService.listaReceitaProdudo(produto);
		}
		return listaReceitas;
	}
	
	
	
	public List<ReceitaProduto> getListaReceitas() {
		return listaReceitas;
	}

	public void recaregaLista(ValueChangeEvent event){
		System.out.println("here "+event.getNewValue());
		getListaReceitas();
	}

	public void setListaReceitas(List<ReceitaProduto> listaReceitas) {
		this.listaReceitas = listaReceitas;
	}


	 
}
