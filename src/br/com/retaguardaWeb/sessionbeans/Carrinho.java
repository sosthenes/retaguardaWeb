package br.com.retaguardaWeb.sessionbeans;

import java.util.Set;

import br.com.retaguardaWeb.entidades.Produto;

public interface Carrinho {

	void adiciona(Produto produto) throws Exception;

	void remove(Produto produto);

	Set<Produto> getProdutos();

	void finalizaCompra();
}