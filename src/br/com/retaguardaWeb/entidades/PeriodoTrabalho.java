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
import javax.persistence.Transient;

@Entity
@Table(name="periodotrabalho")
public class PeriodoTrabalho {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idperiodoTrabalho")
	private Long id;
	
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	private Date horaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date horaFim;

	@ManyToOne
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario = new Funcionario();
	
	@ManyToOne
	@JoinColumn(name="idUsuario")
	private Usuario usuario = new Usuario();
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja idLoja = new Loja();
	
	
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


	public PeriodoTrabalho() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Loja getIdLoja() {
		return idLoja;
	}


	public void setIdLoja(Loja idLoja) {
		this.idLoja = idLoja;
	}


	public Date getHoraInicio() {
		return horaInicio;
	}


	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}


	public Date getHoraFim() {
		return horaFim;
	}


	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
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
	
}
