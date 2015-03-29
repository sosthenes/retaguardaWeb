package br.com.retaguardaWeb.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="moto")
@NamedQuery(name="Moto.findUserByPlaca", query="select u from Moto u where u.placa = :placa")
public class Moto {

	public static final String FIND_BY_PLACA = "Moto.findUserByPlaca";
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idMoto")
	private Long id;

	private String placa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}


	
	
	
}
