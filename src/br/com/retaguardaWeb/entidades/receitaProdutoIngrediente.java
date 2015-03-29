package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="receitaprodutoingrediente")
public class receitaProdutoIngrediente {

	
	@Id @GeneratedValue ( strategy = GenerationType . IDENTITY )
	 private Long id;
	
	 private String quantidade ;
	
	 @ManyToMany
	 private List <Ingrediente> ingredientes = new ArrayList <Ingrediente >() ;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
	 
	 
}
