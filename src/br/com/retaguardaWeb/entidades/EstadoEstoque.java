package br.com.retaguardaWeb.entidades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="estadoestoque")
public class EstadoEstoque  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idestadoEstoque")
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP) 
	private Date dataEstoque;
	
	@ManyToOne
	@JoinColumn(name="idEstoque")
	private Estoque estoque;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idestadoEstoque")
	private List<EstoqueAtual> listaEstadoEstoque = new ArrayList<EstoqueAtual>();
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEstoque() {
		return dataEstoque;
	}

	public void setDataEstoque(Date dataEstoque) {
		this.dataEstoque = dataEstoque;
	}

	public Estoque getEstoque() {
		if(estoque==null)
			estoque = new Estoque();
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<EstoqueAtual> getListaEstadoEstoque() {
		return listaEstadoEstoque;
	}

	public void setListaEstadoEstoque(List<EstoqueAtual> listaEstadoEstoque) {
		this.listaEstadoEstoque = listaEstadoEstoque;
	}





	
	
}