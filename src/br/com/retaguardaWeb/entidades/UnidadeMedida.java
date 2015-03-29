package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="unidademedida")
public class UnidadeMedida  {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idUnidadeMedida")
	private Long id;
	
	@Column(name = "descricao", length = 150)
	private String descricao;
	
	private String sigla;
	private String multiplicador;

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

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getMultiplicador() {
		return multiplicador;
	}

	public void setMultiplicador(String multiplicador) {
		this.multiplicador = multiplicador;
	}
	
	

}