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
@Table(name="tipopedido")
public class TipoVenda extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9066101052408237314L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idTipoPedido")
	private Long id;
	
	@Column(name="descricao")
	private String descricao;
	
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja;
	


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


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}




	
	
	
}
