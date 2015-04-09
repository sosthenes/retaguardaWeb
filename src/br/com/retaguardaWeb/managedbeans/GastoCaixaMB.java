package br.com.retaguardaWeb.managedbeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.TipoGasto;
import br.com.retaguardaWeb.services.CaixaService;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.GastoService;
import br.com.retaguardaWeb.services.TipoGastoService;

@ManagedBean
@ViewScoped
public class GastoCaixaMB  extends BasicoMB{

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private TipoGastoService tipoGastoService;
	@EJB
	private CaixaService caixaService;
	@EJB
	private GastoService gastoService;
	

	
	private Date data;
	
	DecimalFormat qtdeParser = new DecimalFormat( "0.00");
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	private List<Caixa> listaCaixa = new ArrayList<Caixa>();
	private List<GastoCaixa> listaGastoCaixa = new ArrayList<GastoCaixa>();
	private List<TipoGasto> listaTipoGasto = new ArrayList<TipoGasto>();
	private GastoCaixa gastoCaixa = new GastoCaixa();
	
	private Long idFuncionario;
	private Long idTipoGasto;
	private Long idCaixa;
	
	Caixa caixa = new Caixa();
	TipoGasto tipoGasto = new TipoGasto();
	Funcionario funcionario = new Funcionario();
	
	private String totalGasto;
	
	@PostConstruct
	private void init() {
		if(getUsuario().getId()==null && getUsuario().getFuncionario().getId()!=null){
			setIdFuncionario(getUsuario().getFuncionario().getId());
		}
		
		if(funcionarioes==null || funcionarioes.isEmpty()){
			funcionarioes = funcionarioService.getFuncionarios(getLoja());
		}
		
		if(listaCaixa==null || listaCaixa.isEmpty()){
			listaCaixa = caixaService.getCaixas(getLoja());
		}
		
		if(listaTipoGasto==null || listaTipoGasto.isEmpty()){
			listaTipoGasto = tipoGastoService.getTipoGastos(getLoja());
		}
		
		if(getIdCaixa()!=null){
			getCaixa().setId(getIdCaixa());
		}	
		if(listaGastoCaixa==null || listaGastoCaixa.isEmpty())
			listaGastoCaixa = gastoService.listaGastosPorCaixa(getCaixa());

	}
	
	
	public void adiciona () {
		getFuncionario().setId(getIdFuncionario());
		getTipoGasto().setId(getIdTipoGasto());
		getCaixa().setId(getIdCaixa());
		getGastoCaixa().setCaixa(getCaixa());
		getGastoCaixa().setFuncionario(getFuncionario());
		getGastoCaixa().setTipoGasto(getTipoGasto());
		getGastoCaixa().setUsuario(getUsuario());
		getGastoCaixa().setDataHora(getData());
		gastoService.adiciona(getGastoCaixa());
		listaGastoCaixa  = gastoService.listaGastosPorCaixa(getCaixa());;
		gastoCaixa = null;
		idFuncionario = null;
		idTipoGasto = null;
		idCaixa = null;
	 }
	 

	
	
	public void remover() {
		gastoService.remover(gastoCaixa);
		getListaGastoCaixa();
	}
	
	public void listaFuncionarioes() {
		Loja loja = new Loja();
		loja.setId(1L);
		List<Funcionario> lista = new ArrayList<Funcionario>();
		lista = funcionarioService.listaFuncionarioPorLoja(loja, null, null);
		if(!lista.isEmpty()){
			funcionarioes = lista;
		}
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

	public Long getIdTipoGasto() {
		return idTipoGasto;
	}

	public void setIdTipoGasto(Long idTipoGasto) {
		this.idTipoGasto = idTipoGasto;
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

	public TipoGasto getTipoGasto() {
		return tipoGasto;
	}

	public void setTipoGasto(TipoGasto tipoGasto) {
		this.tipoGasto = tipoGasto;
	}

	public void setFuncionarioes(List<Funcionario> funcionarioes) {
		this.funcionarioes = funcionarioes;
	}


	public GastoCaixa getGastoCaixa() {
		return gastoCaixa;
	}

	public void setGastoCaixa(GastoCaixa gastoCaixa) {
		this.gastoCaixa = gastoCaixa;
	}

	
	public List<GastoCaixa> getListaGastoCaixa() {
		return listaGastoCaixa;
	}

	public void setListaGastoCaixa(List<GastoCaixa> listaGastoCaixa) {
		this.listaGastoCaixa = listaGastoCaixa;
	}

	public String getTotalGasto() {
		double total = 0.00;
		for(GastoCaixa gasto : getListaGastoCaixa()){
			total+=gasto.getValor();
		}
		totalGasto = qtdeParser.format(total);
		return totalGasto;
	}

	public void setTotalGasto(String totalGasto) {
		this.totalGasto = totalGasto;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	@Override
	public void listar() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}



	 
	
	
}
