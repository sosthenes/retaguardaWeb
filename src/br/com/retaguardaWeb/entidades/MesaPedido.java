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
@Table(name="mesapedido")
public class MesaPedido extends EntidadeBase{



	/**
	 * 
	 */
	private static final long serialVersionUID = 57588758006906697L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idPedidoMesa")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idMesaLoja")
	private MesaLoja idMesaLoja;

	@ManyToOne
	@JoinColumn(name="idPedido")
	private Pedido idPedido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MesaLoja getIdMesaLoja() {
		return idMesaLoja;
	}

	public void setIdMesaLoja(MesaLoja idMesaLoja) {
		this.idMesaLoja = idMesaLoja;
	}

	public Pedido getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Pedido idPedido) {
		this.idPedido = idPedido;
	}

	
	
	
}
