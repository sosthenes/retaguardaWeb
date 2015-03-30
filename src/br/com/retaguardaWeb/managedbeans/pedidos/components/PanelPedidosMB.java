package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PedidoProduto;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;

@ManagedBean(name="panelPedidosMB")
@ViewScoped
public class PanelPedidosMB {
	
	@EJB
	private CarrinhoBean carrinhoBean;
	private Pedido pedido;

	public List<Produto> getProdutos() {
		return new ArrayList<Produto>(this.carrinhoBean.getProdutos());
	}
	
	public void recarregarPedido() {
		pedido = new Pedido();
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


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
	public Pedido getPedido() {
		return pedido;
	}
	
	
	
	
	
}
