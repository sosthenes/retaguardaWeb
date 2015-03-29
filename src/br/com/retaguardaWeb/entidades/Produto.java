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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="produto")
public class Produto  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idProduto")
	private Long id;
	
	private String descricao;
	
	private double preco;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="id")
	private List<ReceitaProduto> receitaProdutos = new ArrayList<ReceitaProduto>();
	
	@Transient
	private boolean meia;
	
	@ManyToOne
	@JoinColumn(name="idCategoriaProduto", referencedColumnName="idCategoriaProduto")
	private CategoriaProduto categoria = new CategoriaProduto();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return String.format("%d, %s, R$%,.2f", descricao);
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public List<ReceitaProduto> getReceitaProdutos() {
		return receitaProdutos;
	}

	public void setReceitaProdutos(List<ReceitaProduto> receitaProdutos) {
		this.receitaProdutos = receitaProdutos;
	}

	public boolean isMeia() {
		return meia;
	}

	public void setMeia(boolean meia) {
		this.meia = meia;
	}

	public CategoriaProduto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}


	
	
}