package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Fechamentocaixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.entidades.TipoGasto;
import br.com.retaguardaWeb.services.CaixaService;
import br.com.retaguardaWeb.services.FechamentoCaixaService;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.GastoService;
import br.com.retaguardaWeb.services.QuilometragemBoyService;
import br.com.retaguardaWeb.services.TipoGastoService;
import br.com.retaguardaWeb.services.ValeFuncionarioService;
import br.com.retaguardaWeb.util.Conversoes;

@Named
@ViewScoped
public class FechamentocaixaMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private TipoGastoService tipoGastoService;
	@EJB
	private CaixaService caixaService;
	@EJB
	private FechamentoCaixaService fechamentoCaixaService;
	@EJB
	private ValeFuncionarioService valefuncionarioService;
	@EJB
	private QuilometragemBoyService quilometragemBoyService;
	@EJB
	private GastoService gastoService;
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	private List<Caixa> listaCaixa = new ArrayList<Caixa>();
	private List<Fechamentocaixa> listaFechamentocaixa = new ArrayList<Fechamentocaixa>();
	private List<TipoGasto> listaTipoGasto = new ArrayList<TipoGasto>();
	private Fechamentocaixa fechamentocaixa = new Fechamentocaixa();
	
	private Long idFuncionario;
	private Long idCaixa;
	private String totalGasto;
	private String totalDebito;
	private String totalCredito;
	private String totalContaCliente;
	private String totalDinheiro;
	private Date dataFechamento;
	
	Caixa caixa = new Caixa();
	TipoGasto tipoGasto = new TipoGasto();
	Funcionario funcionario = new Funcionario();
	
	
	@PostConstruct
	public void init() {
		if(funcionarioes==null || funcionarioes.isEmpty()){
			funcionarioes = funcionarioService.getFuncionarios(getLoja());
		}
		if(listaCaixa==null || listaCaixa.isEmpty()){
			listaCaixa = caixaService.getCaixas(getLoja());
		}
		if(listaTipoGasto==null || listaTipoGasto.isEmpty()){
			listaTipoGasto = tipoGastoService.getTipoGastos(getLoja());
		}
		if(listaFechamentocaixa==null || listaFechamentocaixa.isEmpty()){
			listar();
		}
		if(getUsuario().getId()==null && getUsuario().getFuncionario().getId()!=null){
			setIdFuncionario(getUsuario().getFuncionario().getId());
		}
	}
	
	
	
	
	public void adiciona () {
		getFuncionario().setId(getIdFuncionario());
		getCaixa().setId(getIdCaixa());
		getFechamentocaixa().setCaixa(getCaixa());
		getFechamentocaixa().setFuncionario(getFuncionario());
		if(getUsuario()!=null && getUsuario().getId()!=null){
			getFechamentocaixa().setUsuario(getUsuario());
		}else{
			getFechamentocaixa().setUsuario(null);
		}
		getFechamentocaixa().setData(getDataFechamento());
		if(!verificaCaixaAberto()){
			fechamentoCaixaService.adiciona(getFechamentocaixa());
			retornaMensagemSucessoOperacao();
			listar();
			limpar();
		}else{
			retornaMensagemErro("J� existe fechamento desse caixa!");
		}
	 }
	 
	private boolean verificaCaixaAberto() {
		for(Fechamentocaixa fc : getListaFechamentocaixa()){
			if(formato.format(fc.getData()).equals(formato.format(getFechamentocaixa().getData())) && fc.getCaixa().getId().equals(getFechamentocaixa().getCaixa().getId())){
				return true;
			}
		}
		return false;
	}

	public void remover() {
		fechamentoCaixaService.remover(fechamentocaixa);
		listar();
	}
	
	public void listaFuncionarioes() {
		Loja loja = new Loja();
		loja.setId(1L);
		List<Funcionario> lista = new ArrayList<Funcionario>();
		Setor setor = new Setor();
		setor.setId(2L);
		lista = funcionarioService.listaFuncionarioPorLoja(null,null,null);
		if(!lista.isEmpty()){
			funcionarioes = lista;
		}
	}
	
	
	public Funcionario getFuncionario() {
		if(funcionario==null)
			funcionario = new Funcionario();
		return funcionario;
	}
	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public List<Funcionario> getFuncionarioes() {
		return funcionarioes;
	}

	public List<Caixa> getListaCaixa() {
		return listaCaixa;
	}

	public void setListaCaixa(List<Caixa> listaCaixa) {
		this.listaCaixa = listaCaixa;
	}

	public List<TipoGasto> getListaTipoGasto() {
		return listaTipoGasto;
	}

	public void setListaTipoGasto(List<TipoGasto> listaTipoGasto) {
		this.listaTipoGasto = listaTipoGasto;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}


	public Long getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	public Caixa getCaixa() {
		if(caixa==null){
			caixa = new Caixa();
		}
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

	public void setFuncionarioes(List<Funcionario> funcionarioes) {
		this.funcionarioes = funcionarioes;
	}


	public Fechamentocaixa getFechamentocaixa() {
		if(fechamentocaixa!=null && fechamentocaixa.getFuncionario()!=null && fechamentocaixa.getCaixa()!=null && fechamentocaixa.getData()!=null){
			setIdFuncionario(fechamentocaixa.getFuncionario().getId());
			setIdCaixa(fechamentocaixa.getCaixa().getId());
			setDataFechamento(fechamentocaixa.getData());
		}
		return fechamentocaixa;
	}

	public void setFechamentocaixa(Fechamentocaixa fechamentocaixa) {
		this.fechamentocaixa = fechamentocaixa;
	}

	
	public List<Fechamentocaixa> getListaFechamentocaixa() {
		return listaFechamentocaixa;
	}

	public void setListaFechamentocaixa(List<Fechamentocaixa> listaFechamentocaixa) {
		this.listaFechamentocaixa = listaFechamentocaixa;
	}



	@Override
	public void listar() {
		if(getIdCaixa()!=null){
			getCaixa().setId(getIdCaixa());
		}
		List<Fechamentocaixa> listaFec = fechamentoCaixaService.fechamentoPorCaixa(getCaixa());
		
		listaFechamentocaixa = listaFec;
		populaGastosCaixa();
		
	}
	
	public void populaGastosCaixa(){
		if(getCaixa()!=null && getCaixa().getId()!=null){
			double vales = valefuncionarioService.getValefuncionariosPorCaixaConsolidado(new Timestamp(System.currentTimeMillis()), getCaixa());
			double gasolina = quilometragemBoyService.getGasolinasPorCaixaConsolidado(new Timestamp(System.currentTimeMillis()), getCaixa());
			double gasto = gastoService.getGastoPorCaixaConsolidado(new Timestamp(System.currentTimeMillis()), getCaixa());
			
			fechamentocaixa.setVale(new Conversoes().converteDoubleToString(vales));
			fechamentocaixa.setGasolina(new Conversoes().converteDoubleToString(gasolina));
			fechamentocaixa.setGasto(new Conversoes().converteDoubleToString(gasto));
		}
	}

	@Override
	public void limpar() {
		setFuncionario(null);
		setCaixa(null);
		setDataFechamento(null);
		fechamentocaixa = new Fechamentocaixa();
	}

	@Override
	public void editar() {
		if(fechamentocaixa!=null){
			atualizaObjetoFunc();
			this.fechamentoCaixaService.adiciona (this.getFechamentocaixa());
			listar();
			limpar();
			retornaMensagemSucessoOperacao();
		}else{
			fechamentocaixa = new Fechamentocaixa();
		}		
	}

	private void atualizaObjetoFunc() {
		getFuncionario().setId(getIdFuncionario());
		getCaixa().setId(getIdCaixa());
		getFechamentocaixa().setCaixa(getCaixa());
		getFechamentocaixa().setFuncionario(getFuncionario());
		getFechamentocaixa().setUsuario(getUsuario());
		getFechamentocaixa().setData(getDataFechamento());

	}

	public String getTotalGasto() {
		double total = 0.00;
		for(Fechamentocaixa fech : getListaFechamentocaixa()){
			total+=Double.parseDouble(fech.getDinheiro().replaceAll(",", "."));
			total+=Double.parseDouble(fech.getDebito().replaceAll(",", "."));
			total+=Double.parseDouble(fech.getCredito().replaceAll(",", "."));
			total+=Double.parseDouble(fech.getContaCliente().replaceAll(",", "."));
		}
		totalGasto = qtdeParser.format(total);
		return totalGasto;
	}

	public void setTotalGasto(String totalGasto) {
		this.totalGasto = totalGasto;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public String getTotalDebito() {
		double total = 0.00;
		for(Fechamentocaixa fech : getListaFechamentocaixa()){
			total+=Double.parseDouble(fech.getDebito().replaceAll(",", "."));
		}
		totalDebito = qtdeParser.format(total);

		return totalDebito;
	}

	public void setTotalDebito(String totalDebito) {
		this.totalDebito = totalDebito;
	}

	public String getTotalCredito() {
		double total = 0.00;
		for(Fechamentocaixa fech : getListaFechamentocaixa()){
			total+=Double.parseDouble(fech.getCredito().replaceAll(",", "."));
		}
		totalCredito = qtdeParser.format(total);

		return totalCredito;
	}

	public void setTotalCredito(String totalCredito) {
		this.totalCredito = totalCredito;
	}

	public String getTotalContaCliente() {
		double total = 0.00;
		for(Fechamentocaixa fech : getListaFechamentocaixa()){
			total+=Double.parseDouble(fech.getContaCliente().replaceAll(",", "."));
		}
		totalContaCliente = qtdeParser.format(total);
		return totalContaCliente;
	}

	public void setTotalContaCliente(String totalContaCliente) {
		this.totalContaCliente = totalContaCliente;
	}

	public String getTotalDinheiro() {
		double total = 0.00;
		for(Fechamentocaixa fech : getListaFechamentocaixa()){
			total+=Double.parseDouble(fech.getDinheiro().replaceAll(",", "."));
		}
		totalDinheiro = qtdeParser.format(total);
		return totalDinheiro;
	}

	public void setTotalDinheiro(String totalDinheiro) {
		this.totalDinheiro = totalDinheiro;
	}



	 
	
	
}
