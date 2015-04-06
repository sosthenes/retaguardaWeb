package br.com.retaguardaWeb.entidades;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="expedicaoPedido")
public class ExpedicaoPedido extends EntidadeBase{


	/**
	 * 
	 */
	private static final long serialVersionUID = -6641418816132808298L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idexpedicaoPedido")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idPedido")
	private Pedido idPedido;
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario idFuncionario;

	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date dataHoraSaida;

	@Temporal(TemporalType.TIMESTAMP) 
	private Date datahoraChegada;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pedido getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Pedido idPedido) {
		this.idPedido = idPedido;
	}

	public Funcionario getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Funcionario idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Date getDataHoraSaida() {
		return dataHoraSaida;
	}

	public void setDataHoraSaida(Date dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}

	public Date getDatahoraChegada() {
		return datahoraChegada;
	}

	public void setDatahoraChegada(Date datahoraChegada) {
		this.datahoraChegada = datahoraChegada;
	}


	
	
	
}
