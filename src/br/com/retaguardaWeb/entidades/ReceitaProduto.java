package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="receita")
public class ReceitaProduto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idReceita")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idProduto")
	private Produto idProduto = new Produto();
	
	@ManyToOne
	@JoinColumn(name="idIngrediente")
	private Ingrediente idIngrediente = new Ingrediente();
	
	@ManyToOne
	@JoinColumn(name="idUnidadeMedida")
	private UnidadeMedida idUnidadeMedida = new UnidadeMedida();
	
	@Column(name="Quantidade")
	private String quantidade;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ingrediente getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Ingrediente idIngrediente) {
		this.idIngrediente = idIngrediente;
	}


	public UnidadeMedida getIdUnidadeMedida() {
		return idUnidadeMedida;
	}

	public void setIdUnidadeMedida(UnidadeMedida idUnidadeMedida) {
		this.idUnidadeMedida = idUnidadeMedida;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Produto idProduto) {
		this.idProduto = idProduto;
	}


	
	
}