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
@Table(name="cargo")
public class Cargo extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3760701716897641278L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idCargo")
	private Long id;

	
	private String descricao;

	private double salarioBruto;

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


	public Cargo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


	public double getSalarioBruto() {
		return salarioBruto;
	}


	public void setSalarioBruto(double salarioBruto) {
		this.salarioBruto = salarioBruto;
	}
	
	
	
	
}
