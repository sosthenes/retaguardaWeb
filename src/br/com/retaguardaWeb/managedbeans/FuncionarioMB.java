package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.entidades.SituacaoFuncional;
import br.com.retaguardaWeb.services.CargoService;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.SetorService;
import br.com.retaguardaWeb.services.SituacaoService;

@ManagedBean(name="funcionarioMB")
@ViewScoped
public class FuncionarioMB extends BasicoMB{

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private SetorService setorService;
	@EJB
	private SituacaoService situacaoService;
	@EJB
	private CargoService cargoService;
	
	
	private List<Funcionario> funcionarioes;
	
	private Funcionario funcionario;
	private SituacaoFuncional situacao;
	
	private Long idLoja;
	private Long idSetor;
	private Long idCargo;
	private Long idSituacaoFuncional;
	
	
	private List<Setor> listaSetor;
	
	private List<SituacaoFuncional> listaSituacao = new ArrayList<SituacaoFuncional>();
	
	private List<Cargo> listaCargo = new ArrayList<Cargo>();
	
	Setor setor = new Setor();
	Cargo cargo = new Cargo();
	
	
	
	@PostConstruct
	private void init() {

		if(funcionario == null){
			funcionario = new Funcionario();
			funcionario.setLoja(getLoja());
		}
		
		
		if(listaSetor==null || listaSetor.isEmpty()){
			listarSetores();
		}
		if(listaCargo==null || listaCargo.isEmpty()){
			listarCargos();
		}
		if(listaSituacao==null || listaSituacao.isEmpty()){
			listarSituacoes();
		}
		
		listar();
	}
	
	
	public void adiciona () {
		if(funcionario!=null){
			funcionario.setLoja(getLoja());
			this.funcionarioService.adiciona(getFuncionario());
			 setFuncionario(new Funcionario());
			 listaFuncionarioes();
			 limpaObjetoFunc();
			 listar();
		}else{
			funcionario = new Funcionario();
		}
	 }
	
	
	public void editar() {
		if(funcionario!=null){
			funcionario.setLoja(getLoja());
			this.funcionarioService.adiciona (this.getFuncionario());
			listaFuncionarioes();
			limpaObjetoFunc();
		}else{
			funcionario = new Funcionario();
		}
	 }
	 
	
	public void atualizaObjetoFunc(){
		getCargo().setId(getIdCargo());
		getSetor().setId(getIdSetor());
		getSituacao().setId(getIdSituacaoFuncional());
		getFuncionario().setLoja(getLoja());
		getFuncionario().setCargo(getCargo());
		getFuncionario().setSetor(getSetor());
		getFuncionario().setSituacaoFuncional(getSituacao());
	}
	
	public void limpaObjetoFunc(){
		getLoja().setId(null);
		getCargo().setId(null);
		getSetor().setId(null);
		getSituacao().setId(null);
		setFuncionario(null);
		setIdLoja(null);
		setIdSetor(null);
		setIdCargo(null);
		setIdSituacaoFuncional(null);
		setFuncionario(new Funcionario());
	}
	
	public void listarSetores(){
		listaSetor = setorService.listaSetorPorLoja(getLoja());
	}
	
	public void listarSituacoes(){
		listaSituacao = situacaoService.listaSituacaoPorLoja(getLoja());
	}
	
	public void listaFuncionarioes() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		if(!lista.isEmpty()){
			lista = funcionarioService.listaFuncionarioPorLoja(getLoja(), setor, cargo);
			funcionarioes = lista;
		}
		
	}
	
	public void remover() {
		funcionarioService.remover(funcionario);
		listaFuncionarioes();
	}
	
	public Funcionario getFuncionario() {
		return funcionario;
	}
	

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public List<Funcionario> getFuncionarioes() {
		return funcionarioes;
	}


	public Long getIdLoja() {
		return idLoja;
	}

	public void setIdLoja(Long idLoja) {
		this.idLoja = idLoja;
	}

	public void setFuncionarioes(List<Funcionario> funcionarioes) {
		this.funcionarioes = funcionarioes;
	}


	public Long getIdSetor() {
		return idSetor;
	}

	public void setIdSetor(Long idSetor) {
		this.idSetor = idSetor;
	}

	public List<Setor> getListaSetor() {
		
		return listaSetor;
	}

	public void setListaSetor(List<Setor> listaSetor) {
		this.listaSetor = listaSetor;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public Long getIdCargo() {
		return idCargo;
	}

	public void setIdCargo(Long idCargo) {
		this.idCargo = idCargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public List<Cargo> getListaCargo() {
		return listaCargo;
	}

	private void listarCargos() {
		
		listaCargo = cargoService.listaCargoPorLoja(getLoja());
		
	}

	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
	}

	public SituacaoFuncional getSituacao() {
		if(situacao==null)
			situacao = new SituacaoFuncional();
		return situacao;
	}

	public void setSituacao(SituacaoFuncional situacao) {
		this.situacao = situacao;
	}

	public Long getIdSituacaoFuncional() {
		return idSituacaoFuncional;
	}

	public void setIdSituacaoFuncional(Long idSituacaoFuncional) {
		this.idSituacaoFuncional = idSituacaoFuncional;
	}

	public List<SituacaoFuncional> getListaSituacao() {
		return listaSituacao;
	}

	public void setListaSituacao(List<SituacaoFuncional> listaSituacao) {
		this.listaSituacao = listaSituacao;
	}


	@Override
	public void listar() {
		funcionarioes = funcionarioService.listaFuncionarioPorLoja(getLoja(), setor, cargo);
	}


	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}
	 
	

	 
	
	
}
