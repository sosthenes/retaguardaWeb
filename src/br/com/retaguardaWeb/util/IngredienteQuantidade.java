package br.com.retaguardaWeb.util;

import br.com.retaguardaWeb.entidades.Ingrediente;

public class IngredienteQuantidade {

	private Ingrediente ingrediente;
	
	private double qtd;
	
	private double quantidadeRetirada;
	
	

	public double getQuantidadeRetirada() {
		return quantidadeRetirada;
	}

	public void setQuantidadeRetirada(double quantidadeRetirada) {
		this.quantidadeRetirada = quantidadeRetirada;
	}

	public Ingrediente getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}

	public double getQtd() {
		return qtd;
	}

	public void setQtd(double qtd) {
		this.qtd = qtd;
	}
	
	
	
	
}
