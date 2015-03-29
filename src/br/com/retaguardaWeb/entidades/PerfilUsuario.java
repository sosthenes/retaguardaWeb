package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="perfilusuario")
public class PerfilUsuario extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4442026909221055919L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPerfilusuario")
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
