package br.com.retaguardaWeb.managedbeans.pedidos;

import javax.inject.Named;
import java.io.Serializable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.ExpedicaoPedido;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.managedbeans.periodoTrabalho.PeriodoTrabalhoMB;
import br.com.retaguardaWeb.services.CaixaService;
import br.com.retaguardaWeb.services.ExpedicaoPedidoService;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.PedidoService;

@ViewScoped
@Named
public class ExpedicaoMB extends BasicoMB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private PedidoService pedidoService;
	
	@EJB
	private FuncionarioService funcionarioService;
	
	@EJB
	private CaixaService caixaService;
	@EJB
	private ExpedicaoPedidoService expedicaoService;

	private ExpedicaoPedido expedicao;
	
	private PeriodoTrabalho periodo;
	private List<Pedido> listaPedido;
	private List<Funcionario> listaFuncionario;
	private Funcionario funcionario;
	private CaixaPeriodoFuncionario caixaPeriodoFuncionario;
	
	@Inject
	private PeriodoTrabalhoMB periodoTrabalhoMB;
	
	@PostConstruct
	public void init() {
		
		
		listaFunc();
		if(expedicao==null){
			expedicao = new ExpedicaoPedido();
		}
		
		if(!periodoTrabalhoMB.isPeriodoAberto()){
			try {
				context.redirect(context.getRequestContextPath() + "/admin/periodo/periodoFechado.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return ;
		}else{
			if(periodo==null){
				periodo = periodoTrabalhoMB.getPeriodo();
			}
		}
		
		
		caixaPeriodoFuncionario = caixaService.recuperaCaixaAberto(getLoja(), periodo, null);
		listar();
	}
	
	public void listaFunc(){
		listaFuncionario = funcionarioService.listaFuncionarioPorLoja(getLoja(), null, null);
	}
	
	public void selecionaFuncionario(Pedido pedido){
		getExpedicao().setIdPedido(pedido);
		RequestContext.getCurrentInstance().execute("PF('modalSelecionaFuncionario').show()");
	}
	
	@Override
	public void adiciona() {
		getExpedicao().setDataHoraSaida(new Date());
		expedicaoService.mantemExpedicao(getExpedicao());
		listar();
		RequestContext.getCurrentInstance().execute("modalSelecionaFuncionario.hide()");
		RequestContext.getCurrentInstance().update("principal:panelPedidos");
		retornaMensagemSucessoOperacao();
		limpar();
	}

	@Override
	public void listar() {
		listaPedido = pedidoService.getPedidosExpedicao(caixaPeriodoFuncionario);
	}

	@Override
	public void remover() {
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



	public List<Pedido> getListaPedido() {
		return listaPedido;
	}



	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}



	public PeriodoTrabalhoMB getPeriodoTrabalhoMB() {
		return periodoTrabalhoMB;
	}



	public void setPeriodoTrabalhoMB(PeriodoTrabalhoMB periodoTrabalhoMB) {
		this.periodoTrabalhoMB = periodoTrabalhoMB;
	}

	public ExpedicaoPedido getExpedicao() {
		return expedicao;
	}

	public void setExpedicao(ExpedicaoPedido expedicao) {
		this.expedicao = expedicao;
	}

	public List<Funcionario> getListaFuncionario() {
		return listaFuncionario;
	}

	public void setListaFuncionario(List<Funcionario> listaFuncionario) {
		this.listaFuncionario = listaFuncionario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

}
