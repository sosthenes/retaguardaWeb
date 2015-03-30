package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Produto;

@ManagedBean(name = "panelProdutosMB")
@ViewScoped
public class PanelProdutosMB {

	private List<Produto> produtosDisponiveis;

	@PostConstruct
	public void init() {
		setProdutosDisponiveis(new ArrayList<Produto>());
	}

	public List<Produto> getProdutosDisponiveis() {
		return produtosDisponiveis;
	}

	public void setProdutosDisponiveis(List<Produto> produtosDisponiveis) {
		this.produtosDisponiveis = produtosDisponiveis;
	}

}
