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
@Table(name="diariafuncionario")
public class Diariafuncionario{

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idDiariaFuncionario")
	private Long id;

	@ManyToOne
	@JoinColumn(name="idCaixa")
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	private String valor;
	@Transient
	private String valorDispivel;
	@Transient
	private String valorUtilizado;
	
	@Transient
	private String valorPermitido;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataDiaria;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	
	@Transient
	 public String getMyFormattedDate() {
		if(dataDiaria!=null){
	        return new SimpleDateFormat("dd/MM/yyyy").format(getDataDiaria());
		}else{
			return "";
		}
	    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Date getDataDiaria() {
		return dataDiaria;
	}

	public void setDataDiaria(Date dataDiaria) {
		this.dataDiaria = dataDiaria;
	}

	public String getValorDispivel() {
		return valorDispivel;
	}

	public void setValorDispivel(String valorDispivel) {
		this.valorDispivel = valorDispivel;
	}

	public String getValorUtilizado() {
		return valorUtilizado;
	}

	public void setValorUtilizado(String valorUtilizado) {
		this.valorUtilizado = valorUtilizado;
	}

	public String getValorPermitido() {
		return valorPermitido;
	}

	public void setValorPermitido(String valorPermitido) {
		this.valorPermitido = valorPermitido;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	

	
}
