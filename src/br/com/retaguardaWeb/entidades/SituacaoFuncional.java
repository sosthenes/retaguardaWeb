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
@Table(name="situacaofuncional")
public class SituacaoFuncional extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4410149778286450732L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idSituacaoFuncional")
	private Long id;

	
	private String descricao;


	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
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


	public SituacaoFuncional() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}
	
	
	
	
}
