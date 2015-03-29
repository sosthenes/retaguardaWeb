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
@Table(name="tipodepagamento")
public class TipoDePagamento extends EntidadeBase{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7668420931430508053L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idTipoPagamento")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja;

	
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


	public TipoDePagamento() {
		super();
	}


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


	
}
