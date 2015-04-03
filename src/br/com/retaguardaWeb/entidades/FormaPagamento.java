package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="formapagamento")
public class FormaPagamento   extends EntidadeBase{





	/**
	 * 
	 */
	private static final long serialVersionUID = -5927031682968114102L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idFormaPagamento")
	private Long id;

	
	private String descricao;


	
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




	
}
