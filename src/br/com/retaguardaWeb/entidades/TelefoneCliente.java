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
@Table(name="telefonecliente")
public class TelefoneCliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idtelefoneCliente")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idCliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="idOperadora")
	private Operadora operadora;
	
	private String numero;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Operadora getOperadora() {
		return operadora;
	}

	public void setOperadora(Operadora operadora) {
		this.operadora = operadora;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
	
	
	
}
