package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.services.CategoriaService;
import br.com.retaguardaWeb.services.ClienteService;
import br.com.retaguardaWeb.services.ProdutoService;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;

@Named
@ViewScoped
public class PanelCategoriasMB implements Serializable {
private static final long serialVersionUID = 1L;

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
