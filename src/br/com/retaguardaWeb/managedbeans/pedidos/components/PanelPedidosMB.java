package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PedidoProduto;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.TipoVenda;
import br.com.retaguardaWeb.services.PedidoService;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;

@ManagedBean(name="panelPedidosMB")
@ViewScoped
public class PanelPedidosMB {
	
	@EJB
	private CarrinhoBean carrinhoBean;
	@EJB
	private PedidoService pedidoService;

	
	private Pedido pedido;
	
	@PostConstruct
	public void init() {
		if(pedido==null)
			pedido = new Pedido();
	}
	

	public List<Produto> getProdutos() {
		return new ArrayList<Produto>(this.carrinhoBean.getProdutos());
	}
	
	public void adicionarProdutoAPedidos(Produto produto, Boolean meia) {
		try {
			if (meia) {
				carrinhoBean.adicionaMeia(produto);
			} else {
				carrinhoBean.adiciona(produto);
			}
			recarregarPedido();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finalizaPedido(){
		carrinhoBean.finalizaCompra();
	}
	

	
	public void abreModalTipoPedido() {
		RequestContext.getCurrentInstance().execute("modalTipoPedido.show()");
		RequestContext.getCurrentInstance().update("principal:modalTipoPedido");
	}


	
	public void recarregarPedido() {
		pedido.setPedidoProdutos(new ArrayList<PedidoProduto>());
		boolean verifica = false;
		double total = 0.0;
		double parcial = 0.0;
		for (Produto prod : getProdutos()) {
			PedidoProduto pedidoProduto = new PedidoProduto();
			if (prod != null && prod.isMeia()) {
				if (prod.isMeia() && !verifica) {
					verifica = true;
					pedidoProduto.setProdutos(prod);
					pedidoProduto.setIdPedido(pedido);
					pedido.getPedidoProdutos().add(pedidoProduto);
				} else {
					pedido.getPedidoProdutos()
							.get(pedido.getPedidoProdutos().size() - 1)
							.setProdutosMeia(prod);
					if (pedido.getPedidoProdutos()
							.get(pedido.getPedidoProdutos().size() - 1)
							.getProdutos().getPreco() > prod.getPreco()) {
						parcial = pedido.getPedidoProdutos()
								.get(pedido.getPedidoProdutos().size() - 1)
								.getProdutos().getPreco();
					} else {
						parcial = prod.getPreco();
					}
					pedido.getPedidoProdutos()
							.get(pedido.getPedidoProdutos().size() - 1)
							.getProdutos().setPreco(parcial);
					total += parcial;
					verifica = false;
				}
			} else {
				pedidoProduto.setProdutos(prod);
				pedidoProduto.setIdPedido(pedido);
				pedido.getPedidoProdutos().add(pedidoProduto);
				total += prod.getPreco();
			}
		}
		pedido.setTotalPedido(total);
		// totalPedido();
	}
	public void remove(PedidoProduto pedidoProduto) {
		this.carrinhoBean.remove(pedidoProduto.getProdutos());
		recarregarPedido();
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Pedido getPedido() {
		return pedido;
	}

	public void cadastraPedido() {
		// TODO Auto-generated method stub
		try {
			carrinhoBean.cadastraPedido(pedido);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<Pedido> listaPedidoPorCaixa(CaixaPeriodoFuncionario caixaPeriodoFuncionario, TipoVenda tipoVenda) {
		return pedidoService.getPedidos(caixaPeriodoFuncionario, null, tipoVenda);
	}

	
	
	
}
