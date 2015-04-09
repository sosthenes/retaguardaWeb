package br.com.retaguardaWeb.managedbeans;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.Estoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.MinimoEstoque;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.ReceitaProduto;
import br.com.retaguardaWeb.entidades.RetiradaEstoque;
import br.com.retaguardaWeb.services.EstoqueAtualServices;
import br.com.retaguardaWeb.services.EstoqueServices;
import br.com.retaguardaWeb.services.IngredienteService;
import br.com.retaguardaWeb.services.ProdutoService;
import br.com.retaguardaWeb.services.ReceitasServices;
import br.com.retaguardaWeb.util.EstoqueAtualUtil;
import br.com.retaguardaWeb.util.IngredienteQuantidade;

@ManagedBean
public class VendaManualInteirasMB {

	@EJB
	private ProdutoService produtoService;
	
	@EJB
	private IngredienteService ingredienteService;
	@EJB
	private EstoqueAtualServices estadoAtualService;
	@EJB
	private EstoqueServices estoqueService;
	
	@EJB
	ReceitasServices receitasServices;
	
	List<IngredienteQuantidade> listaQuantidadeIngrediente  = new ArrayList<IngredienteQuantidade>();
	private List<EstoqueAtualUtil> listaEstoqueAtualUtil = new ArrayList<EstoqueAtualUtil>();
	
	private Estoque estoque;
	private List <MinimoEstoque> minimoEstoque ;
	
	manterEstoqueMB estoqueMB = new manterEstoqueMB();
	
	private List<Produto> listaProduto;
	private List<Produto> listaProdutoVendido;
	
	EstadoEstoque estadoEstoque = new EstadoEstoque();

	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	DecimalFormat fmt = new DecimalFormat("0.000"); 
	
	
	
	private Date dataInicio;
	private Date dataFim;
	
	
	

	@PostConstruct
	private void init() {
		if(listaProduto==null || listaProduto.size()<1)
			carregaProdutos();
	}
	
	
	public void pesquisar() {
		listaProdutoVendido = produtoService.recuperaListaProdutoVendido(dataInicio, dataFim);
	}
	
	public void carregaProdutos(){
		listaProduto = produtoService.getProdutos();
	}
	
	
	public void atualizaEstoque(){
		
        String[] listaQuantidade = request.getParameterValues("listaQuatidade");
		
		int i=0;
		
		
		for(Produto produto :  getListaProduto()){
			if(listaQuantidade[i]==""){
				listaQuantidade[i] = "0";
			}
			estoqueService.retiraIngredientesEstoque(produto, listaQuantidade[i], estadoEstoque, listaQuantidadeIngrediente);	
			i++;
		}
		
		estadoEstoque = estoqueService.listaEstadoEstoque(listaEstoqueAtualUtil, getEstoque(), minimoEstoque, estadoEstoque);
		
		List<EstoqueAtual> estoqueAtual= new ArrayList<EstoqueAtual>();
		
		estoqueService.salvaRetiradas(listaEstoqueAtualUtil, listaQuantidadeIngrediente, fmt, estoqueAtual);
		
		estadoEstoque.setId(null);
		estadoEstoque.setListaEstadoEstoque(estoqueAtual);
		estadoEstoque.setDataEstoque(new Timestamp(System.currentTimeMillis()));
		estoque.setId(1L);
		estadoEstoque.setEstoque(estoque);
		estadoAtualService.adiciona(estadoEstoque);
	}
	
	
	public List<EstoqueAtualUtil> getListaEstoqueAtualUtil() {
		return listaEstoqueAtualUtil;
	}


	public void setListaEstoqueAtualUtil(
			List<EstoqueAtualUtil> listaEstoqueAtualUtil) {
		this.listaEstoqueAtualUtil = listaEstoqueAtualUtil;
	}


	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}


	public Estoque getEstoque() {
		if(estoque==null)
			estoque = new Estoque();
		return estoque;
	}


	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}


	public List<IngredienteQuantidade> getListaQuantidadeIngrediente() {
		return listaQuantidadeIngrediente;
	}


	public void setListaQuantidadeIngrediente(
			List<IngredienteQuantidade> listaQuantidadeIngrediente) {
		this.listaQuantidadeIngrediente = listaQuantidadeIngrediente;
	}


	public Date getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	public Date getDataFim() {
		return dataFim;
	}


	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}


	public List<Produto> getListaProdutoVendido() {
		return listaProdutoVendido;
	}


	public void setListaProdutoVendido(List<Produto> listaProdutoVendido) {
		this.listaProdutoVendido = listaProdutoVendido;
	}




	
}
