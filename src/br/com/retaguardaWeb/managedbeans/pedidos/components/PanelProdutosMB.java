package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.retaguardaWeb.entidades.Produto;

@Named
@ViewScoped
public class PanelProdutosMB implements Serializable{
private static final long serialVersionUID = 1L;

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
