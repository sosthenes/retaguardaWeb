package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="estoque")
public class Estoque  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idEstoque")
	private Long id;
	
	private String nomeEstoque;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="idEstoqueMinimoIngrediente")
	private List<MinimoEstoque> minimosEstoque = new ArrayList<MinimoEstoque>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeEstoque() {
		return nomeEstoque;
	}

	public void setNomeEstoque(String nomeEstoque) {
		this.nomeEstoque = nomeEstoque;
	}

	public List<MinimoEstoque> getMinimosEstoque() {
		return minimosEstoque;
	}

	public void setMinimosEstoque(List<MinimoEstoque> minimosEstoque) {
		this.minimosEstoque = minimosEstoque;
	}





	
	
}