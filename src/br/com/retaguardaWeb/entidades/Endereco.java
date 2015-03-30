package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="endereco")
public class Endereco extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Endereco() {
		// TODO Auto-generated constructor stub
	}
	public Endereco(Cliente cliente) {
		setCliente(cliente);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idEndereco")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idCliente",referencedColumnName="id",nullable=false)
	private Cliente cliente;

	@Column(name="descricao")
	private String descricao;
	
	@Column(name="cep")
	private String cep;
	
	@Override
	public Long getId() {
		return id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
}
