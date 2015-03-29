package br.com.retaguardaWeb.entidades;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="advertencia")
public class Advertencia{

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idAdvertencia")
	private Long id;

	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;
	
	private String descricao;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataFato;

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


	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	@Transient
	 public String getMyFormattedDate() {
		if(data!=null){
	        return new SimpleDateFormat("dd/MM/yyyy").format(getData());
		}else{
			return "";
		}
	    }
	
	@Transient
	 public String getMyFormattedDateFato() {
		if(data!=null){
	        return new SimpleDateFormat("dd/MM/yyyy").format(getDataFato());
		}else{
			return "";
		}
	    }

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}
	
}
