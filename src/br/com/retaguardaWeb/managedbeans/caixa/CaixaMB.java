package br.com.retaguardaWeb.managedbeans.caixa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.entidades.ValoresFechamentoCaixa;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.managedbeans.periodoTrabalho.PeriodoTrabalhoMB;
import br.com.retaguardaWeb.sessionbeans.CaixaService;

@ViewScoped
@ManagedBean(name="caixaMB")
public class CaixaMB extends BasicoMB{

	
	@EJB
	private CaixaService caixaService;

	@ManagedProperty("#{periodoTrabalhoMB}")
	private PeriodoTrabalhoMB periodoTrabalhoMB;
	
	
	private List<Caixa> listaCaixaDisponivel;
	private Caixa caixa;
	private List<CaixaPeriodoFuncionario> listaCaixaPeriodoFuncionario;
	private CaixaPeriodoFuncionario caixaPeriodoFuncionario;
	private boolean caixaAberto;
	private CaixaPeriodoFuncionario valoresCaixa;
	private PeriodoTrabalho periodoTrabalho;
	
	private List<ValoresFechamentoCaixa> listaValoresFechamentoCaixa;
	
	
	@PostConstruct
	public void init() {
		
		if(!periodoTrabalhoMB.isPeriodoAberto()){
			try {
				context.redirect(context.getRequestContextPath() + "/admin/periodo/periodoFechado.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			periodoTrabalho = periodoTrabalhoMB.getPeriodo();
			
			if(listaValoresFechamentoCaixa==null)
				listaValoresFechamentoCaixa = new ArrayList<ValoresFechamentoCaixa>();
		}
		
		if(caixaPeriodoFuncionario==null){
			caixaPeriodoFuncionario = new CaixaPeriodoFuncionario();
		}
		
		if(isCaixaAberto()){
			valoresCaixa = new CaixaPeriodoFuncionario();
			valoresCaixa = caixaService.recuperaCaixaAberto(getLoja(), periodoTrabalho, getUsuario().getFuncionario());
			valoresCaixa = pegaValoresCaixa(periodoTrabalho, valoresCaixa);
			populaListaValoresAlteracao();
			
		}
		listar();
	}
	
	private void populaListaValoresAlteracao() {
		for(ValoresFechamentoCaixa c : valoresCaixa.getListaValoresFechamentoCaixa()){
			listaValoresFechamentoCaixa.add(c);
		}
	}

	public CaixaPeriodoFuncionario pegaValoresCaixa(PeriodoTrabalho periodoTrabalhoAtual, CaixaPeriodoFuncionario valoresCaixaAtual) {
		return caixaService.pegaValoresCaixa(periodoTrabalhoAtual,valoresCaixaAtual);
	}

	public boolean verificaCaixaAberto(){
		caixaAberto = caixaService.verificaCaixaAberto(getLoja(), periodoTrabalho, getUsuario().getFuncionario());
		return caixaAberto;
	}
	
	public CaixaPeriodoFuncionario recuperaCaixaAberto(){
		return caixaService.recuperaCaixaAberto(getLoja(), periodoTrabalho, getUsuario().getFuncionario());
	}
	
	@Override
	public void adiciona() {
		caixaPeriodoFuncionario.setFuncionario(getUsuario().getFuncionario());
		caixaPeriodoFuncionario.setIdCaixa(getCaixa());
		caixaPeriodoFuncionario.setPeriodoTrabalho(periodoTrabalho);
		caixaPeriodoFuncionario.setDataHoraAbertura(new Date());
		caixaPeriodoFuncionario.setDataHoraFechamento(null);
		caixaPeriodoFuncionario.setIdLoja(getLoja());
		caixaPeriodoFuncionario = caixaService.atualizaCaixa(caixaPeriodoFuncionario);
		retornaMensagemSucesso("Caixa aberto com Sucesso!");
	}

	@Override
	public void listar() {
		listaCaixaPeriodoFuncionario = caixaService.recuperaCaixaAberto(getLoja(), periodoTrabalho);
		listaCaixaDisponivel = caixaService.getCaixas(getLoja());
		if(listaCaixaDisponivel!=null && listaCaixaDisponivel.size()>0){
			for(CaixaPeriodoFuncionario t : listaCaixaPeriodoFuncionario){
				if(listaCaixaDisponivel.contains(t.getIdCaixa())){
					listaCaixaDisponivel.remove(t.getIdCaixa());
				}
			}
		}
	}

	@Override
	public void remover() {
		populaCPF();
		caixaService.atualizaCaixa(getCaixaPeriodoFuncionario());
		retornaMensagemSucesso("Caixa fechado com Sucesso! Bom descanso!");
	}

	private void populaCPF() {
		getCaixaPeriodoFuncionario().setId(valoresCaixa.getId());
		getCaixaPeriodoFuncionario().setDataHoraFechamento(new Date());
		getCaixaPeriodoFuncionario().setIdCaixa(valoresCaixa.getIdCaixa());
		getCaixaPeriodoFuncionario().setIdLoja(valoresCaixa.getIdLoja());
		getCaixaPeriodoFuncionario().setPeriodoTrabalho(valoresCaixa.getPeriodoTrabalho());
		getCaixaPeriodoFuncionario().setFuncionario(valoresCaixa.getFuncionario());
		getCaixaPeriodoFuncionario().setDataHoraAbertura(valoresCaixa.getDataHoraAbertura());
		getCaixaPeriodoFuncionario().setValorInicial(valoresCaixa.getValorInicial());
		getCaixaPeriodoFuncionario().setValorTotalVendido(valoresCaixa.getValorTotalVendido());
		getCaixaPeriodoFuncionario().setListaValoresFechamentoCaixa(listaValoresFechamentoCaixa);
		
		
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}


	public List<Caixa> getListaCaixaDisponivel() {
		return listaCaixaDisponivel;
	}


	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public PeriodoTrabalhoMB getPeriodoTrabalhoMB() {
		return periodoTrabalhoMB;
	}

	public void setPeriodoTrabalhoMB(PeriodoTrabalhoMB periodoTrabalhoMB) {
		this.periodoTrabalhoMB = periodoTrabalhoMB;
	}

	public List<CaixaPeriodoFuncionario> getListaCaixaPeriodoFuncionario() {
		return listaCaixaPeriodoFuncionario;
	}

	public void setListaCaixaPeriodoFuncionario(
			List<CaixaPeriodoFuncionario> listaCaixaPeriodoFuncionario) {
		this.listaCaixaPeriodoFuncionario = listaCaixaPeriodoFuncionario;
	}

	public CaixaPeriodoFuncionario getCaixaPeriodoFuncionario() {
		return caixaPeriodoFuncionario;
	}

	public void setCaixaPeriodoFuncionario(
			CaixaPeriodoFuncionario caixaPeriodoFuncionario) {
		this.caixaPeriodoFuncionario = caixaPeriodoFuncionario;
	}

	public boolean isCaixaAberto() {
		return verificaCaixaAberto();
	}

	public void setCaixaAberto(boolean caixaAberto) {
		this.caixaAberto = caixaAberto;
	}

	public CaixaPeriodoFuncionario getValoresCaixa() {
		return valoresCaixa;
	}

	public void setValoresCaixa(CaixaPeriodoFuncionario valoresCaixa) {
		this.valoresCaixa = valoresCaixa;
	}

	public List<ValoresFechamentoCaixa> getListaValoresFechamentoCaixa() {
		return listaValoresFechamentoCaixa;
	}

	public void setListaValoresFechamentoCaixa(
			List<ValoresFechamentoCaixa> listaValoresFechamentoCaixa) {
		this.listaValoresFechamentoCaixa = listaValoresFechamentoCaixa;
	}
	
	

}
