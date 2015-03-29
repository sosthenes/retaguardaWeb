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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="cliente")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data_nascimento;
	
	private String nome;
	
	private String email;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="idTelefoneCliente")
	private List<TelefoneCliente> listaTelefones = new ArrayList<TelefoneCliente>();

	@Transient
	TelefoneCliente telefone = new TelefoneCliente();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData_nascimento() {
		return data_nascimento;
	}

	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<TelefoneCliente> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(List<TelefoneCliente> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	public TelefoneCliente getTelefone() {
		if(telefone==null)
			telefone = new TelefoneCliente();
		
		System.out.println(telefone.getNumero());
		return telefone;
	}

	public void setTelefone(TelefoneCliente telefone) {
		this.telefone = telefone;
	}
	
	
}
