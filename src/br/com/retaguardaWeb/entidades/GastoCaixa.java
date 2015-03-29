package br.com.retaguardaWeb.entidades;

import java.text.SimpleDateFormat;
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
import javax.persistence.Transient;

@Entity
@Table(name="gastocaixa")
public class GastoCaixa {

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idGastoCaixa")
	private Long id;
	

	@ManyToOne
	@JoinColumn(name="idCaixa")
	private Caixa caixa;
	
	@ManyToOne
	@JoinColumn(name="idTipoGasto")
	private TipoGasto tipoGasto;
	
	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataHora;
	
	private double valor;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Caixa getCaixa() {
		return caixa;
	}
	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	public TipoGasto getTipoGasto() {
		return tipoGasto;
	}
	public void setTipoGasto(TipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Date getDataHora() {
		return dataHora;
	}
	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	@Transient
	 public String getMyFormattedDate() {
		if(dataHora!=null){
	        return new SimpleDateFormat("dd/MM/yyyy").format(getDataHora());
		}else{
			return "";
		}
	    }
	
}
