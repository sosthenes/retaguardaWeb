package br.com.retaguardaWeb.managedbeans.pedidos;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.MesaLoja;
import br.com.retaguardaWeb.entidades.MesaPedido;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.entidades.TipoVenda;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.managedbeans.FormaPagamentoMB;
import br.com.retaguardaWeb.managedbeans.TipoVendaMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.BuscaClientePorTelefoneMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.PanelPedidosMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.SelecionarEnderecoMB;
import br.com.retaguardaWeb.managedbeans.periodoTrabalho.PeriodoTrabalhoMB;
import br.com.retaguardaWeb.services.CaixaService;
import br.com.retaguardaWeb.util.Acao;

@Named
@ViewScoped
public class CadastroPedidoMB extends BasicoMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private CaixaService caixaService;

	@Inject
	private BuscaClientePorTelefoneMB buscaClientePorTelefoneMB;

	@Inject
	private SelecionarEnderecoMB selecionarEnderecoMB;

	@Inject
	private PanelPedidosMB panelPedidosMB;

	@Inject
	private TipoVendaMB tipoVendaMB;

	@Inject
	private FormaPagamentoMB formaPagamentoMB;

	@Inject
	private PeriodoTrabalhoMB periodoTrabalhoMB;

	private Double valorPago;

	private Double valorTroco;

	private Pedido pedidoCliente;

	private PeriodoTrabalho periodo;
	private CaixaPeriodoFuncionario caixaPeriodoFuncionario;
	private List<Pedido> listaPedido;
	private List<MesaPedido> listaMesasSelecionadas;

	public void init() {

		if (!periodoTrabalhoMB.isPeriodoAberto()) {
			try {
				context.redirect(context.getRequestContextPath()
						+ "/admin/periodo/periodoFechado.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		} else {
			if (periodo == null) {
				periodo = periodoTrabalhoMB.getPeriodo();
			}
			panelPedidosMB.getPedido();
		}

		if (!caixaService.verificaCaixaAberto(getLoja(), periodo, getUsuario()
				.getFuncionario())) {
			try {
				context.redirect(context.getRequestContextPath()
						+ "/admin/caixa/caixa.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			caixaPeriodoFuncionario = caixaService.recuperaCaixaAberto(
					getLoja(), periodo, getUsuario().getFuncionario());
		}

		buscaClientePorTelefoneMB
				.setCallbackCarregarCliente(new Acao<Cliente>() {
					private SelecionarEnderecoMB selecionarEnderecoClosureMB = selecionarEnderecoMB;

					@Override
					public void executar(Cliente cliente) {
						selecionarEnderecoClosureMB.setCliente(cliente);
						RequestContext.getCurrentInstance().update(
								"principal:panelSelecionarEndereco");
					}
				});
	}

	public void adicionaMesas(MesaLoja[] lista) {
		listaMesasSelecionadas = new ArrayList<MesaPedido>();
		Arrays.asList(lista).forEach((m) -> {
			MesaPedido mesa = new MesaPedido();
			mesa.setIdMesaLoja(m);
			mesa.setIdPedido(panelPedidosMB.getPedido());
			listaMesasSelecionadas.add(mesa);
		});
		panelPedidosMB.getPedido().setMesas(listaMesasSelecionadas);
		RequestContext.getCurrentInstance()
				.execute("PF('modalSelecionaMesa').hide()");
		RequestContext.getCurrentInstance().update("principal:mesaSelecionada");

	}

	public void removeMesaSelecionada(MesaPedido mesa) {
		listaMesasSelecionadas.remove(mesa);
		retornaMensagemSucessoOperacao();
		RequestContext.getCurrentInstance().update("principal:mesaSelecionada");
	}

	public void adicionaTipoVenda() {
		panelPedidosMB.getPedido().setTipoPedido(tipoVendaMB.getTipoVenda());
		panelPedidosMB.getPedido().setExpedicao(tipoVendaMB.isExpedicao());
		RequestContext.getCurrentInstance().execute("PF('modalTipoPedido').hide()");
		RequestContext.getCurrentInstance().execute(
				"PF('modalFormaPagamento').show()");

	}

	public void adicionaFormaPagamento() {
		panelPedidosMB.getPedido().setFormaPagamento(
				formaPagamentoMB.getFormaPagamento());
		RequestContext.getCurrentInstance().execute(
				"PF('modalFormaPagamento').hide()");
		RequestContext.getCurrentInstance().execute("PF('modalTroco').show()");
	}

	public void adicionaTroco() {
		panelPedidosMB.getPedido().setValorPago(valorPago);
		if (valorPago != null && valorPago > 0) {
			valorTroco = valorPago
					- panelPedidosMB.getPedido().getTotalPedido();
		} else {
			valorTroco = 0.00;
			valorPago = panelPedidosMB.getPedido().getTotalPedido();
		}

		panelPedidosMB.getPedido().setValorTroco(valorTroco);
		RequestContext.getCurrentInstance().execute("PF('modalTroco').hide()");
		RequestContext.getCurrentInstance().execute("PF('modalPago').show()");
	}

	public void adicionaPagamento(boolean pago) {
		panelPedidosMB.getPedido().setPago(pago);
		panelPedidosMB.getPedido().setDataHoraPagamento(
				pago ? new Date() : null);
		adiciona();
		RequestContext.getCurrentInstance().execute("PF('modalPago').hide()");
		RequestContext.getCurrentInstance().execute("PF('modalFinal').show()");
		RequestContext.getCurrentInstance().update("principal:panelPedidos");
		listar();
	}

	public void finalizaPedido() {
		limpar();
		try {
			context.redirect(context.getRequestContextPath()
					+ "/admin/pedido/cadastroPedido.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PanelPedidosMB getPanelPedidosMB() {
		return panelPedidosMB;
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

	@Override
	public void adiciona() {
		if (selecionarEnderecoMB.getEnderecoSelecionado() != null)
			panelPedidosMB.getPedido().setEnderecoEntrega(
					selecionarEnderecoMB.getEnderecoSelecionado());
		panelPedidosMB.getPedido().setCaixaPeriodoFuncionario(
				caixaPeriodoFuncionario);
		if (panelPedidosMB.getPedido().getEnderecoEntrega() != null
				&& panelPedidosMB.getPedido().getIdCliente() == null)
			panelPedidosMB.getPedido().setIdCliente(
					panelPedidosMB.getPedido().getEnderecoEntrega()
							.getCliente().getId());

		panelPedidosMB.cadastraPedido();
		panelPedidosMB.finalizaPedido();
		retornaMensagemSucesso("Pedido registrato com sucesso!");
	}

	@Override
	public void listar() {
		listaPedido = panelPedidosMB.listaPedidoPorCaixa(
				caixaPeriodoFuncionario, null);
	}

	public List<Pedido> listaPedidoPorCaixaTipoVenda(
			CaixaPeriodoFuncionario caixaPeriodoFuncionario, TipoVenda tipoVenda) {
		return panelPedidosMB.listaPedidoPorCaixa(caixaPeriodoFuncionario,
				tipoVenda);
	}

	public void listaPedidosPorTipo(TipoVenda tipoVenda) {
		listaPedido = listaPedidoPorCaixaTipoVenda(caixaPeriodoFuncionario,
				tipoVenda);
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub

	}

	@Override
	public void limpar() {
		panelPedidosMB.setPedido(null);
		valorPago = null;
		valorTroco = null;
		pedidoCliente = null;
		periodo = null;
		caixaPeriodoFuncionario = null;
		formaPagamentoMB.setFormaPagamento(null);
		formaPagamentoMB.setFormaPagamento(null);
		buscaClientePorTelefoneMB.setClienteSelecionado(null);
		selecionarEnderecoMB.setCliente(new Cliente());
		listaMesasSelecionadas = new ArrayList<MesaPedido>();
		listaPedido = new ArrayList<Pedido>();

	}

	public void recuperaPedido(Pedido pedido) {
		panelPedidosMB.setPedido(pedido);
		RequestContext.getCurrentInstance().execute("PF('modalPago').show()");
	}

	@Override
	public void editar() {

	}

	public FormaPagamentoMB getFormaPagamentoMB() {
		return formaPagamentoMB;
	}

	public PeriodoTrabalhoMB getPeriodoTrabalhoMB() {
		return periodoTrabalhoMB;
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
		if (listaPedido == null || listaPedido.size() < 1) {
			listar();
		}
		return listaPedido;
	}

	public void setListaPedido(List<Pedido> listaPedido) {
		this.listaPedido = listaPedido;
	}

	public List<MesaPedido> getListaMesasSelecionadas() {
		return listaMesasSelecionadas;
	}

	public void setListaMesasSelecionadas(
			List<MesaPedido> listaMesasSelecionadas) {
		this.listaMesasSelecionadas = listaMesasSelecionadas;
	}

}
