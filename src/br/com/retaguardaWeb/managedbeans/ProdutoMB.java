package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.services.CategoriaService;
import br.com.retaguardaWeb.services.ProdutoService;

@ManagedBean
public class ProdutoMB {

	@EJB
	private ProdutoService produtoService;
	@EJB
	private CategoriaService categoriaService;
	
	private Long idCategoria;
	
	private java.util.List<Produto> produtos  = new ArrayList<Produto>();
	
	private Produto produto = new Produto();
	
	public void adiciona () {
		if(produto!=null){
			this.produtoService.adiciona (this.produto);
		 this.produto= new Produto();
		}else{
			produto = new Produto();
		}
	 }
	 
	 
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public List<Produto> getProdutos() {
		if(produtos==null || produtos.isEmpty()){
			if(idCategoria!=null){
				produto.getCategoria().setId(idCategoria);
				CategoriaProduto categoria = new CategoriaProduto();
				categoria.setId(idCategoria);
				produto.setCategoria(categoria);
				produtos = produtoService.listaProdudo(categoria);
			}
		}
		return produtos;
	}


	public void setProdutos(java.util.List<Produto> produtos) {
		this.produtos = produtos;
	}
	 
	
	public List<CategoriaProduto> getListaCategoriaProduto(){
		return categoriaService.getCategorias();
	}


	public Long getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	 
	
	
}
