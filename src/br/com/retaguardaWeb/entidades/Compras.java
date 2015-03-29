package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="compra")
public class Compras {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCompra")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
	private String numeroNota;

	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date dataCompra;
	
	@ManyToOne
	@JoinColumn(name="idFornecedor")
	private Fornecedor fornecedor = new Fornecedor();
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="idItemCompra")
	private List<ItemDeCompra> itemCompra = new ArrayList<ItemDeCompra>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(String numeroNota) {
		this.numeroNota = numeroNota;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<ItemDeCompra> getItemCompra() {
		return itemCompra;
	}

	public void setItemCompra(List<ItemDeCompra> itemCompra) {
		this.itemCompra = itemCompra;
	}

	public Loja getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}
	
	
	
	
}
