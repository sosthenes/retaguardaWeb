package br.com.retaguardaWeb.managedbeans;

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
import br.com.retaguardaWeb.entidades.Valefuncionario;
import br.com.retaguardaWeb.sessionbeans.FuncionarioService;
import br.com.retaguardaWeb.sessionbeans.RelatorioService;
import br.com.retaguardaWeb.sessionbeans.ValeFuncionarioService;
import br.com.retaguardaWeb.util.Conversoes;

@ManagedBean
@ViewScoped
public class ValeFuncionarioMB extends BasicoMB{

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private ValeFuncionarioService valefuncionarioService;
	@EJB
	private RelatorioService relatorioService;
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	private List<Valefuncionario> lista = new ArrayList<Valefuncionario>();
	
	private List<String> id;
	
	private Long idFuncionario;
	private Setor setor = new Setor();
	private Valefuncionario vale;
	private Funcionario funcionario;
	private Date dataFato;
	private Date dataConsulta;
	private Long idCaixa;
	Caixa caixa = new Caixa();
	
	
	@PostConstruct
	private void init() {
		if(getUsuario().getId()==null && getUsuario().getFuncionario().getId()!=null){
			setIdFuncionario(getUsuario().getFuncionario().getId());
			atualizaValorFuncionario();
		}
		if(funcionarioes==null || funcionarioes.isEmpty()){
			listaFuncionarioes();
		}

		if(dataConsulta==null){
			dataConsulta = new Date();
			
		}
		
		if(lista==null || lista.isEmpty()){
			listar();
		}
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
	public void adiciona() {
		if(funcionario==null){
			funcionario = new Funcionario();
		}
		funcionario.setId(getIdFuncionario());
		if(permiteVale()){
			vale.setFuncionario(funcionario);
			vale.setDataVale(getDataFato());
			vale.setUsuario(getUsuario());
			getCaixa().setId(getIdCaixa());
			vale.setCaixa(getCaixa());
			vale = valefuncionarioService.adiciona(vale);
			getVale().setValor(null);
			setDataFato(null);
			getVale().setDataVale(null);
			atualizaValorFuncionario();
			retornaMensagemSucessoOperacao();
		}
	}

	
	private boolean permiteVale() {
		double valor_disponivel = 0L;
		double valor_solicitado = 0L;
		valor_disponivel = Double.parseDouble(getVale().getValorDispivel().replaceAll(",", "."));
		valor_solicitado = Double.parseDouble(getVale().getValor().replaceAll(",", "."));
		if(valor_solicitado<=valor_disponivel){
			return true;
		}else{
			retornaMensagemErro("O valor solicitado ultrapassa o valor pormitido por mês!");
			return false;
		}
	}


	@Override
	public void listar() {
		if(getIdFuncionario()!=null){
			lista = valefuncionarioService.getValefuncionariosPorFuncionario(getIdFuncionario(), dataConsulta);
		}else{
			lista = null;
		}
	}

	@Override
	public void remover() {
		valefuncionarioService.remover(getAdv());
		retornaMensagemSucessoOperacao();
		atualizaValorFuncionario();
		listar();
	}

	@Override
	public void limpar() {
		setIdFuncionario(null);
		vale = new Valefuncionario();
		dataFato = null;
	}

	public void atualizaValorFuncionario(){
		if(getIdFuncionario()!=null && getIdFuncionario()>0){
			getFuncionario().setId(getIdFuncionario());
			setFuncionario(funcionarioService.pesquisaPorId(getFuncionario()));
			getVale().setValorUtilizado(valefuncionarioService.pegaValorUtilizadoMes(getIdFuncionario(), dataConsulta));
			getVale().setValorDispivel(calcularValorDispVale(getVale().getValorUtilizado(), funcionario.getCargo().getSalarioBruto(), 30));
			listar();
		}
	}

	private String calcularValorDispVale(String valorUtilizado, double salarioBruto, double porcentagem) {
		double valorDisponivel = 0L;
		getVale().setValorPermitido(new Conversoes().converteDoubleToString(((salarioBruto/100)*porcentagem)));
		valorDisponivel = ((salarioBruto/100)*porcentagem)-Double.parseDouble(valorUtilizado.replaceAll(",","."));
		return new Conversoes().converteDoubleToString(valorDisponivel);
	}


	public void listaFuncionarioes() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		getSetor().setId(3L);
		lista = funcionarioService.listaFuncionarioPorLoja(getLoja(), null, null);
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



	public Valefuncionario getAdv() {
		if(vale==null)
			vale = new Valefuncionario();
		return vale;
	}

	public void setAdv(Valefuncionario vale) {
		this.vale = vale;
	}

	public List<Valefuncionario> getLista() {
		return lista;
	}

	public void setLista(List<Valefuncionario> lista) {
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
		valefuncionarioService.gerarAdv(vale);
	}

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}


	public Valefuncionario getVale() {
		if(vale==null){
			vale = new Valefuncionario();
		}
		return vale;
	}


	public void setVale(Valefuncionario vale) {
		this.vale = vale;
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
