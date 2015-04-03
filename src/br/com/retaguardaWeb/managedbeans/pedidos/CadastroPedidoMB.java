package br.com.retaguardaWeb.managedbeans.pedidos;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PedidoProduto;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.managedbeans.FormaPagamentoMB;
import br.com.retaguardaWeb.managedbeans.TipoVendaMB;
import br.com.retaguardaWeb.managedbeans.caixa.CaixaAuxiliarMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.BuscaClientePorTelefoneMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.PanelPedidosMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.SelecionarEnderecoMB;
import br.com.retaguardaWeb.managedbeans.periodoTrabalho.PeriodoTrabalhoMB;
import br.com.retaguardaWeb.util.Acao;

@ManagedBean(name="cadastroPedidoMB")
@ViewScoped
public class CadastroPedidoMB extends BasicoMB{
	
	private static final String ENDERECO_SALVO_COM_SUCESSO = "Endereco salvo com sucesso";
	

	
	@ManagedProperty("#{caixaAuxiliarMB}")
	private CaixaAuxiliarMB caixaMB;
	
	@ManagedProperty("#{buscaClientePorTelefoneMB}")
	private BuscaClientePorTelefoneMB buscaClientePorTelefoneMB;
	
	@ManagedProperty("#{selecionarEnderecoMB}")
	private SelecionarEnderecoMB selecionarEnderecoMB;
	
	@ManagedProperty("#{panelPedidosMB}")
	private PanelPedidosMB panelPedidosMB;
	
	@ManagedProperty("#{tipoVendaMB}")
	private TipoVendaMB tipoVendaMB;
	
	@ManagedProperty("#{formaPagamentoMB}")
	private FormaPagamentoMB formaPagamentoMB;
	
	@ManagedProperty("#{periodoTrabalhoMB}")
	private PeriodoTrabalhoMB periodoTrabalhoMB;
	
	private Double valorPago;
	
	private Double valorTroco;
	
	private Pedido pedidoCliente;
	
	private PeriodoTrabalho periodo;
	private CaixaPeriodoFuncionario caixaPeriodoFuncionario;
	private List<Pedido> listaPedido;
	
	@PostConstruct
	public String init() {
		
		if(!periodoTrabalhoMB.isPeriodoAberto()){
			try {
				context.redirect(context.getRequestContextPath() + "/admin/periodo/periodoFechado.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			if(periodo==null){
				periodo = periodoTrabalhoMB.getPeriodo();
			}
			panelPedidosMB.getPedido();
		}
		
		if(!caixaMB.getCaixaService().verificaCaixaAberto(getLoja(), periodo, getUsuario().getFuncionario())){
			try {
				context.redirect(context.getRequestContextPath() + "/admin/caixa/caixa.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			caixaPeriodoFuncionario = caixaMB.getCaixaService().recuperaCaixaAberto(getLoja(), periodo, getUsuario().getFuncionario());
		}
		
		buscaClientePorTelefoneMB.setCallbackCarregarCliente(new Acao<Cliente>() {
			private SelecionarEnderecoMB selecionarEnderecoClosureMB = selecionarEnderecoMB;
			@Override
			public void executar(Cliente cliente) {
				selecionarEnderecoClosureMB.setCliente(cliente);
				RequestContext.getCurrentInstance().update("principal:panelSelecionarEndereco");
			}
		});
		return null;
	}

	
	
	public void setSelecionarEnderecoMB(SelecionarEnderecoMB selecionarEnderecoMB) {
		this.selecionarEnderecoMB = selecionarEnderecoMB;
	}

	public void setBuscaClientePorTelefoneMB(
			BuscaClientePorTelefoneMB buscaClientePorTelefoneMB) {
		this.buscaClientePorTelefoneMB = buscaClientePorTelefoneMB;
	}
	
	
	
	
	
	public void adicionaTipoVenda(){
		panelPedidosMB.getPedido().setTipoPedido(tipoVendaMB.getTipoVenda());
		RequestContext.getCurrentInstance().execute("modalTipoPedido.hide()");
		RequestContext.getCurrentInstance().execute("modalFormaPagamento.show()");
		
	}

	public void adicionaFormaPagamento(){
		panelPedidosMB.getPedido().setFormaPagamento(formaPagamentoMB.getFormaPagamento());
		RequestContext.getCurrentInstance().execute("modalFormaPagamento.hide()");
		RequestContext.getCurrentInstance().execute("modalTroco.show()");
	}
	
	
	public void adicionaTroco(){
		panelPedidosMB.getPedido().setValorPago(valorPago);
		if(valorPago!=null && valorPago >0){
			valorTroco = valorPago - panelPedidosMB.getPedido().getTotalPedido();
		}else{
			valorTroco = 0.00;
			valorPago = panelPedidosMB.getPedido().getTotalPedido();
		}
		panelPedidosMB.getPedido().setValorTroco(valorTroco);
		RequestContext.getCurrentInstance().execute("modalTroco.hide()");
		adiciona();
	}
	
	
	
	
	public PanelPedidosMB getPanelPedidosMB() {
		return panelPedidosMB;
	}

	public void setPanelPedidosMB(PanelPedidosMB panelPedidosMB) {
		this.panelPedidosMB = panelPedidosMB;
	}

	public Pedido getPedidoCliente() {
		return pedidoCliente;
	}

	public void setPedidoCliente(Pedido pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}

	public TipoVendaMB getTipoVendaMB() {
		return tipoVendaMB;
	}

	public void setTipoVendaMB(TipoVendaMB tipoVendaMB) {
		this.tipoVendaMB = tipoVendaMB;
	}

	@Override
	public void adiciona() {
		panelPedidosMB.getPedido().setEnderecoEntrega(selecionarEnderecoMB.getEnderecoSelecionado());
		panelPedidosMB.getPedido().setCaixaPeriodoFuncionario(caixaPeriodoFuncionario);
		panelPedidosMB.getPedido().setIdCliente(panelPedidosMB.getPedido().getEnderecoEntrega().getCliente().getId());
		
		System.out.println("Pedido");
		System.out.println(panelPedidosMB.getPedido().getEnderecoEntrega().getCliente().getNome());
		System.out.println(panelPedidosMB.getPedido().getEnderecoEntrega().getDescricao());
		System.out.println(panelPedidosMB.getPedido().getTotalPedido());
		System.out.println(panelPedidosMB.getPedido().getFormaPagamento().getDescricao());
		System.out.println(panelPedidosMB.getPedido().getTipoPedido().getDescricao());
		System.out.println(panelPedidosMB.getPedido().getValorPago());
		System.out.println(panelPedidosMB.getPedido().getValorTroco());
		for(PedidoProduto pedido : panelPedidosMB.getPedido().getPedidoProdutos()){
			System.out.println(pedido.getProdutos().getDescricao());
		}
		System.out.println(panelPedidosMB.getPedido().getTotalPedido());
		panelPedidosMB.cadastraPedido();
		limpar();
			panelPedidosMB.finalizaPedido();
			retornaMensagemSucesso("Pedido registrato com sucesso!");
			RequestContext.getCurrentInstance().update(":principal:panelSelecionarEndereco");
			RequestContext.getCurrentInstance().update(":principal:panelProdutos");
	}

	@Override
	public void listar() {
		listaPedido = panelPedidosMB.listaPedidoPorCaixa(caixaPeriodoFuncionario);
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpar() {
		valorPago = null;
		valorTroco = null;
		pedidoCliente = null;
		periodo = null;
		caixaPeriodoFuncionario=null;
		panelPedidosMB.setPedido(null);
		formaPagamentoMB.setFormaPagamento(null);
		formaPagamentoMB.setFormaPagamento(null);
		buscaClientePorTelefoneMB.setClienteSelecionado(null);
		
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	public FormaPagamentoMB getFormaPagamentoMB() {
		return formaPagamentoMB;
	}

	public void setFormaPagamentoMB(FormaPagamentoMB formaPagamentoMB) {
		this.formaPagamentoMB = formaPagamentoMB;
	}

	public PeriodoTrabalhoMB getPeriodoTrabalhoMB() {
		return periodoTrabalhoMB;
	}

	public void setPeriodoTrabalhoMB(PeriodoTrabalhoMB periodoTrabalhoMB) {
		this.periodoTrabalhoMB = periodoTrabalhoMB;
	}



	public CaixaAuxiliarMB getCaixaMB() {
		return caixaMB;
	}



	public void setCaixaMB(CaixaAuxiliarMB caixaMB) {
		this.caixaMB = caixaMB;
	}



	public CaixaPeriodoFuncionario getCaixaPeriodoFuncionario() {
		return caixaPeriodoFuncionario;
	}

	public void setCaixaPeriodoFuncionario(
			CaixaPeriodoFuncionario caixaPeriodoFuncionario) {
		this.caixaPeriodoFuncionario = caixaPeriodoFuncionario;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}

	public Double getValorTroco() {
		return valorTroco;
	}

	public void setValorTroco(Double valorTroco) {
		this.valorTroco = valorTroco;
	}


	public List<Pedido> getListaPedido() {
		if(listaPedido==null || listaPedido.size()<1){
			listar();
		}
		return listaPedido;
	}


	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}	
	
	
	
}
