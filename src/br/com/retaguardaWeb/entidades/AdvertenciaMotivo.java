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
@Table(name="advertenciamotivo")
public class AdvertenciaMotivo {

	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idAdvertenciaMotivo")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idAdvertencia")
	private Advertencia advertencia;
	
	
	@ManyToOne
	@JoinColumn(name="idMotivo")
	private Motivo motivo;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Advertencia getAdvertencia() {
		return advertencia;
	}


	public void setAdvertencia(Advertencia advertencia) {
		this.advertencia = advertencia;
	}


	public Motivo getMotivo() {
		return motivo;
	}


	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}
	
	
	
	
}
