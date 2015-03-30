package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;
import br.com.retaguardaWeb.sessionbeans.CategoriaService;
import br.com.retaguardaWeb.sessionbeans.ClienteService;
import br.com.retaguardaWeb.sessionbeans.ProdutoService;

@ManagedBean(name="panelCategoriasMB")
@ViewScoped
public class PanelCategoriasMB {

	@EJB
	private CarrinhoBean carrinhoBean;
	
	@EJB
	private ClienteService clienteService;
	@EJB
	private CategoriaService categoriaService;
	@EJB
	private ProdutoService produtoService;
	
	private List<CategoriaProduto> categoriasDisponiveis;
	
	@ManagedProperty(value="#{panelProdutosMB}")
	private PanelProdutosMB panelProdutosMB;
	
	@PostConstruct
	private void init() {
		setCategoriasDisponiveis(categoriaService.getCategorias());
	}
	

	public List<CategoriaProduto> getCategoriasDisponiveis() {
		return categoriasDisponiveis;
	}

	public void setCategoriasDisponiveis(
			List<CategoriaProduto> categoriasDisponiveis) {
		this.categoriasDisponiveis = categoriasDisponiveis;
	}
	
	public void mostrarProdutos(CategoriaProduto categoria) {
		panelProdutosMB.setProdutosDisponiveis(produtoService.listaProdudo(categoria));
	}

	public void setPanelProdutosMB(PanelProdutosMB panelProdutosMB) {
		this.panelProdutosMB = panelProdutosMB;
	}
	
	
	
	
}
