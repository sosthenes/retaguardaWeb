package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;

@ManagedBean(name = "panelProdutosMB")
@ViewScoped
public class PanelProdutosMB {

	private List<Produto> produtosDisponiveis;

	@EJB
	private CarrinhoBean carrinhoBean;

	@ManagedProperty(value = "panelPedidosMB")
	private PanelPedidosMB panelPedidosMB;

	public void init() {
		setProdutosDisponiveis(new ArrayList<Produto>());
	}

	public List<Produto> getProdutosDisponiveis() {
		return produtosDisponiveis;
	}

	public void setProdutosDisponiveis(List<Produto> produtosDisponiveis) {
		this.produtosDisponiveis = produtosDisponiveis;
	}



	public void adicionarProdutoAPedidos(Produto produto, Boolean meia) {
		try {
			if (meia) {
				carrinhoBean.adicionaMeia(produto);
			} else {
				carrinhoBean.adiciona(produto);
			}
			panelPedidosMB.recarregarPedido();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void setPanelPedidosMB(PanelPedidosMB panelPedidosMB) {
		this.panelPedidosMB = panelPedidosMB;
	}

}
