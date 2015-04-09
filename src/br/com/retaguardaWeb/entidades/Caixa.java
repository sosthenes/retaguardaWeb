package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="caixa")
@NamedQueries(@NamedQuery(query="select x from Caixa x where x.idLoja=:loja",name=Caixa.OBTER_CAIXAS_POR_LOJA))
public class Caixa extends EntidadeBase{
	
	
	
	public static final String OBTER_CAIXAS_POR_LOJA = "obterCaixasPorLoja";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3447368990681683715L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idCaixa")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
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

	public Loja getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}
	
	
	
}
