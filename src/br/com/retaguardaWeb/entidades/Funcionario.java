package br.com.retaguardaWeb.entidades;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "funcionario")
@NamedQueries({ @NamedQuery(query = "select x from Funcionario x where x.login = :login and x.senha = :senha", name = Funcionario.OBTER_USUARIO_POR_LOGIN_E_SENHA) })
public class Funcionario extends EntidadeBase {

	public static final String OBTER_USUARIO_POR_LOGIN_E_SENHA = "obterUsuarioPorLoginESenha";
	/**
	 * 
	 */
	private static final long serialVersionUID = 5413472301836910156L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idFuncionario")
	private Long id;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date data_nascimento;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataAdminissao;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date dataDemissao;
	private String horarioTrabalhoInicio;
	private String horarioTrabalhoFim;
	private String nomePai;
	private String nomeMae;
	private String ctps;
	private String login;
	private String senha;

	@ManyToOne
	@JoinColumn(name = "idPerfilUsuario")
	private PerfilUsuario idPerfilUsuario;

	@ManyToOne
	@JoinColumn(name = "idCargo")
	private Cargo cargo;

	@ManyToOne
	@JoinColumn(name = "idLoja")
	private Loja loja;

	@ManyToOne
	@JoinColumn(name = "idSetor")
	private Setor setor;

	@ManyToOne
	@JoinColumn(name = "idSituacaoFuncional")
	private SituacaoFuncional situacaoFuncional;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idDependente")
	private List<Dependente> dependentes = new ArrayList<Dependente>();

	private String banco;
	private String agencia;
	private String conta;
	private String moto;
	private String placa;
	private String renavam;

	private String nome;

	private String telefone;
	private String celular;

	private String nomeReferencia1;
	private String telefoneReferencia1;

	private String nomeReferencia2;
	private String telefoneReferencia2;

	private String endereco;

	private String cpf;
	private String rg;
	private String naturalidade;
	private String pis;
	private String tituloEleitor;

	@Override
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
		if (nome != null && !nome.equals("")) {
			this.nome = nome.toUpperCase();
		} else {
			this.nome = nome;
		}
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNomeReferencia1() {
		return nomeReferencia1;
	}

	public void setNomeReferencia1(String nomeReferencia1) {
		this.nomeReferencia1 = nomeReferencia1;
	}

	public String getTelefoneReferencia1() {
		return telefoneReferencia1;
	}

	public void setTelefoneReferencia1(String telefoneReferencia1) {
		this.telefoneReferencia1 = telefoneReferencia1;
	}

	public String getNomeReferencia2() {
		return nomeReferencia2;
	}

	public void setNomeReferencia2(String nomeReferencia2) {
		this.nomeReferencia2 = nomeReferencia2;
	}

	public String getTelefoneReferencia2() {
		return telefoneReferencia2;
	}

	public void setTelefoneReferencia2(String telefoneReferencia2) {
		this.telefoneReferencia2 = telefoneReferencia2;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getTituloEleitor() {
		return tituloEleitor;
	}

	public void setTituloEleitor(String tituloEleitor) {
		this.tituloEleitor = tituloEleitor;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public SituacaoFuncional getSituacaoFuncional() {
		return situacaoFuncional;
	}

	public void setSituacaoFuncional(SituacaoFuncional situacaoFuncional) {
		this.situacaoFuncional = situacaoFuncional;
	}

	public List<Dependente> getDependentes() {
		return dependentes;
	}

	public void setDependentes(List<Dependente> dependentes) {
		this.dependentes = dependentes;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Date getDataAdminissao() {
		return dataAdminissao;
	}

	public void setDataAdminissao(Date dataAdminissao) {
		this.dataAdminissao = dataAdminissao;
	}

	public Date getDataDemissao() {
		return dataDemissao;
	}

	public void setDataDemissao(Date dataDemissao) {
		this.dataDemissao = dataDemissao;
	}

	public String getHorarioTrabalhoInicio() {
		return horarioTrabalhoInicio;
	}

	public void setHorarioTrabalhoInicio(String horarioTrabalhoInicio) {
		this.horarioTrabalhoInicio = horarioTrabalhoInicio;
	}

	public String getHorarioTrabalhoFim() {
		return horarioTrabalhoFim;
	}

	public void setHorarioTrabalhoFim(String horarioTrabalhoFim) {
		this.horarioTrabalhoFim = horarioTrabalhoFim;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getCtps() {
		return ctps;
	}

	public void setCtps(String ctps) {
		this.ctps = ctps;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getMoto() {
		return moto;
	}

	public void setMoto(String moto) {
		this.moto = moto;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getRenavam() {
		return renavam;
	}

	public void setRenavam(String renavam) {
		this.renavam = renavam;
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

	public PerfilUsuario getIdPerfilUsuario() {
		return idPerfilUsuario;
	}

	public void setIdPerfilUsuario(PerfilUsuario idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

}
