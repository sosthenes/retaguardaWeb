package br.com.retaguardaWeb.util;

import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.MinimoEstoque;
import br.com.retaguardaWeb.entidades.RetiradaEstoque;

public class EstoqueAtualUtil {

	private MinimoEstoque listaIngrediente;
	private EstadoEstoque estadoEstoque;
	private EstoqueAtual estoqueAtual;
	private String qtdReposicao;
	private RetiradaEstoque retiradaEstoque;
	
	
	
	public RetiradaEstoque getRetiradaEstoque() {
		return retiradaEstoque;
	}
	public void setRetiradaEstoque(RetiradaEstoque retiradaEstoque) {
		this.retiradaEstoque = retiradaEstoque;
	}
	public String getQtdReposicao() {
		return qtdReposicao;
	}
	public void setQtdReposicao(String qtdReposicao) {
		this.qtdReposicao = qtdReposicao;
	}
	public MinimoEstoque getListaIngrediente() {
		return listaIngrediente;
	}
	public void setListaIngrediente(MinimoEstoque listaIngrediente) {
		this.listaIngrediente = listaIngrediente;
	}
	public EstadoEstoque getEstadoEstoque() {
		return estadoEstoque;
	}
	public void setEstadoEstoque(EstadoEstoque estadoEstoque) {
		this.estadoEstoque = estadoEstoque;
	}
	public EstoqueAtual getEstoqueAtual() {
		return estoqueAtual;
	}
	public void setEstoqueAtual(EstoqueAtual estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	
	
	
	
}
