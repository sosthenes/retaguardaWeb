package br.com.retaguardaWeb.entidades;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario extends EntidadeBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7520786419155292180L;

	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column(name="idUsuario")
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	private String endereco;
	
	private String telefone;
	
	private String email;
	
	@ManyToOne
	@JoinColumn(name="idLoja")
	private Loja loja;
	
	@ManyToOne
	@JoinColumn(name="idPerfilUsuario")
	private PerfilUsuario idPerfilUsuario;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idFuncionario")
	private Funcionario funcionario;

	

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}



	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PerfilUsuario getIdPerfilUsuario() {
		if(idPerfilUsuario==null)
			idPerfilUsuario = new PerfilUsuario();
		return idPerfilUsuario;
	}

	public void setIdPerfilUsuario(PerfilUsuario idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}





	
	
}
