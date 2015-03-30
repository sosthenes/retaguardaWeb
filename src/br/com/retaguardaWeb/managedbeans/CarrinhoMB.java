package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.PedidoProduto;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.TelefoneCliente;
import br.com.retaguardaWeb.sessionbeans.CarrinhoBean;
import br.com.retaguardaWeb.sessionbeans.CategoriaService;
import br.com.retaguardaWeb.sessionbeans.ClienteService;
import br.com.retaguardaWeb.sessionbeans.ProdutoService;

@ManagedBean
@ViewScoped
public class CarrinhoMB {

	@EJB
	private CarrinhoBean carrinhoBean;

	@EJB
	private ProdutoService produtoService;

	@EJB
	private ClienteService clienteService;

	@EJB
	private CategoriaService categoriaService;

	private Cliente cliente;
	List<TelefoneCliente> results = new ArrayList<TelefoneCliente>();
	List<Produto> listaProduto;
	private boolean meia;
	private List<CategoriaProduto> categoriaProduto;
	private CategoriaProduto categoria;
	private Cliente clienteSelecionado;

	@PostConstruct
	private void init() {
		categoriaProduto = this.categoriaService.getCategorias();
		setCliente(new Cliente());
	}

	public List<TelefoneCliente> complete(String query) {
		cliente.getTelefone().setNumero(query);
		results = clienteService.buscaClientePorTelefone(cliente.getTelefone());

		return results;
	}

	private Pedido pedido;

	public void pegaCliente(TelefoneCliente tel) {
		System.out.println(tel.getNumero());
		cliente.getTelefone().getNumero();
	}

	private Produto produto;

	public List<Produto> getProdutos() {
		return new ArrayList<Produto>(this.carrinhoBean.getProdutos());
	}

	public void adicionaMeia(Produto produto) {
		try {
			this.carrinhoBean.adicionaMeia(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void adiciona(Produto produto) {
		try {
			this.carrinhoBean.adiciona(produto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregaProdudos() {
		try {
			listaProduto = produtoService.listaProdudo(this.categoria);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void carregarEndereco() {
		setClienteSelecionado(clienteSelecionado);
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public void remove(PedidoProduto pedidoProduto) {
		this.carrinhoBean.remove(pedidoProduto.getProdutos());
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public Pedido getPedido() {
		pedido = new Pedido();
		populaPedido();
		return pedido;
	}

	private void populaPedido() {
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

	public boolean isMeia() {
		return meia;
	}

	public void setMeia(boolean meia) {
		this.meia = meia;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<TelefoneCliente> getResults() {
		return results;
	}

	public void setResults(List<TelefoneCliente> results) {
		this.results = results;
	}

	public List<CategoriaProduto> getCategoriaProduto() {
		return categoriaProduto;
	}

	public void setCategoriaProduto(List<CategoriaProduto> categoriaProduto) {
		this.categoriaProduto = categoriaProduto;
	}

	public CategoriaProduto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaProduto categoria) {
		this.categoria = categoria;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}