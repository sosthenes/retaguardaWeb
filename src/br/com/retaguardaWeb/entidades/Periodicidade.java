package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="periodicidade")
public class Periodicidade   extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4768638142382388981L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPeriodicidade")
	private Long id;

	
	private String descricao;

	private Integer numeroDeDias;

	
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


	public Periodicidade() {
		super();
	}


	public Integer getNumeroDeDias() {
		return numeroDeDias;
	}


	public void setNumeroDeDias(Integer numeroDeDias) {
		this.numeroDeDias = numeroDeDias;
	}


	
}
