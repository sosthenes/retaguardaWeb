package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.retaguardaWeb.util.Conversoes;

@Entity
@Table(name="valoresFechamentoCaixa")
public class ValoresFechamentoCaixa extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6638655610057195235L;


	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idValoresFechamentoCaixa")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idFormaPagamento")
	private FormaPagamento idFormaPagamento;
	
	@ManyToOne
	@JoinColumn(name="idCaixaPeriodoTrabalho")
	private CaixaPeriodoFuncionario caixaPeriodoTrabalho;
	
	private String valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormaPagamento getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(FormaPagamento idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}




	public CaixaPeriodoFuncionario getCaixaPeriodoTrabalho() {
		return caixaPeriodoTrabalho;
	}

	public void setCaixaPeriodoTrabalho(CaixaPeriodoFuncionario caixaPeriodoTrabalho) {
		this.caixaPeriodoTrabalho = caixaPeriodoTrabalho;
	}

	public String getValor() {
		return valor;
	}
	

	public void setValor(String valor) {
		this.valor = valor;
	}

}
