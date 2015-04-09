package br.com.retaguardaWeb.managedbeans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.entidades.Diariafuncionario;
import br.com.retaguardaWeb.services.DiariaFuncionarioService;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.RelatorioService;
import br.com.retaguardaWeb.util.Conversoes;

@ManagedBean
@ViewScoped
public class DiariaFuncionarioMB extends BasicoMB{

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private DiariaFuncionarioService diariafuncionarioService;
	@EJB
	private RelatorioService relatorioService;
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	private List<Diariafuncionario> lista = new ArrayList<Diariafuncionario>();
	
	private List<String> id;
	private Date dataConsulta;
	private Long idFuncionario;
	private Setor setor = new Setor();
	private Diariafuncionario diaria;
	private Funcionario funcionario;
	private Date dataFato;
	private Long idCaixa;
	Caixa caixa = new Caixa();
	
	@PostConstruct
	private void init() {
		if(lista==null || lista.isEmpty()){
			listar();
		}

		if(funcionarioes==null || funcionarioes.isEmpty()){
			listaFuncionarioes();
		}
		if(dataConsulta==null){
			dataConsulta = new Date();
			
		}
	}
	
	
	
	@Override
	public void adiciona() {
		if(funcionario==null){
			funcionario = new Funcionario();
		}
		funcionario.setId(getIdFuncionario());
			diaria.setFuncionario(funcionario);
			diaria.setDataDiaria(getDataFato());
			diaria.setUsuario(getUsuario());
			getCaixa().setId(getIdCaixa());
			diaria.setCaixa(getCaixa());
			diaria = diariafuncionarioService.adiciona(diaria);
			getDiaria().setValor(null);
			setDataFato(null);
			getDiaria().setDataDiaria(null);
			atualizaValorFuncionario();
			retornaMensagemSucessoOperacao();
	}

	
	public void mesAnterior() {
		Calendar c = Calendar.getInstance();
		c.setTime(dataConsulta);
		c.add(Calendar.MONTH, -1);
		dataConsulta = new java.sql.Date(c.getTimeInMillis());
		atualizaValorFuncionario();
		listar();
		
	}
	public void proximoMes() {
		Calendar c = Calendar.getInstance();
		c.setTime(dataConsulta);
		c.add(Calendar.MONTH, 1);
		dataConsulta = new java.sql.Date(c.getTimeInMillis());
		atualizaValorFuncionario();
	}


	@Override
	public void listar() {
		if(getIdFuncionario()!=null){
			lista = diariafuncionarioService.getDiariafuncionariosPorFuncionario(getIdFuncionario(), dataConsulta);
		}else{
			lista = null;
		}
	}

	@Override
	public void remover() {
		diariafuncionarioService.remover(getAdv());
		retornaMensagemSucessoOperacao();
		atualizaValorFuncionario();
		listar();
	}

	@Override
	public void limpar() {
		setIdFuncionario(null);
		diaria = new Diariafuncionario();
		dataFato = null;
	}

	public void atualizaValorFuncionario(){
		getFuncionario().setId(getIdFuncionario());
		setFuncionario(funcionarioService.pesquisaPorId(getFuncionario()));
		getDiaria().setValorUtilizado(diariafuncionarioService.pegaValorUtilizadoMes(getIdFuncionario(), dataConsulta));
		getDiaria().setValorDispivel(calcularValorDispDiaria(getDiaria().getValorUtilizado(), funcionario.getCargo().getSalarioBruto(), 30));
		listar();
	}

	private String calcularValorDispDiaria(String valorUtilizado, double salarioBruto, double porcentagem) {
		double valorDisponivel = 0L;
		getDiaria().setValorPermitido(new Conversoes().converteDoubleToString(((salarioBruto/100)*porcentagem)));
		valorDisponivel = ((salarioBruto/100)*porcentagem)-Double.parseDouble(valorUtilizado.replaceAll(",","."));
		return new Conversoes().converteDoubleToString(valorDisponivel);
	}


	public void listaFuncionarioes() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		getSetor().setId(3L);
		lista = funcionarioService.listaFuncionarioPorLoja(null, null, null);
		if(!lista.isEmpty()){
			funcionarioes = lista;
		}
	}

	public List<Funcionario> getFuncionarioes() {
		
	
		return funcionarioes;
	}

	public void setFuncionarioes(List<Funcionario> funcionarioes) {
		this.funcionarioes = funcionarioes;
	}

	public Setor getSetor() {
		if(setor==null){
			setor = new Setor();
		}
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}



	public Diariafuncionario getAdv() {
		if(diaria==null)
			diaria = new Diariafuncionario();
		return diaria;
	}

	public void setAdv(Diariafuncionario diaria) {
		this.diaria = diaria;
	}

	public List<Diariafuncionario> getLista() {
		return lista;
	}

	public void setLista(List<Diariafuncionario> lista) {
		this.lista = lista;
	}



	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Funcionario getFuncionario() {
		if(funcionario==null)
			funcionario = new Funcionario();
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<String> getId() {
		return id;
	}

	public void setId(List<String> id) {
		this.id = id;
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	
	public void gerarRecibo() {
		diariafuncionarioService.gerarAdv(diaria);
	}

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}


	public Diariafuncionario getDiaria() {
		if(diaria==null){
			diaria = new Diariafuncionario();
		}
		return diaria;
	}


	public void setDiaria(Diariafuncionario diaria) {
		this.diaria = diaria;
	}


	public Long getIdCaixa() {
		return idCaixa;
	}


	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}


	public Caixa getCaixa() {
		return caixa;
	}


	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

}
