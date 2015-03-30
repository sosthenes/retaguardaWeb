package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@Transient
	private Boolean enderecoSelecionado;
	
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
	public Boolean getEnderecoSelecionado() {
		return enderecoSelecionado;
	}
	public void setEnderecoSelecionado(Boolean enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
	
	
}
