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
@Table(name="descricaogasto")
public class DescricaoPagamento extends EntidadeBase {


	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6414935961228478043L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="iddescricaogasto")
	private Long id;

	@Column(name="descricao")
	private String descricaogasto;
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
	

	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}





	public String getDescricaogasto() {
		return descricaogasto;
	}


	public void setDescricaogasto(String descricaogasto) {
		this.descricaogasto = descricaogasto;
	}


	public DescricaoPagamento() {
		super();
	}


	
}
