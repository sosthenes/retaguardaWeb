package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.Estoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.Ingrediente;
import br.com.retaguardaWeb.entidades.MinimoEstoque;
import br.com.retaguardaWeb.services.EstoqueAtualServices;
import br.com.retaguardaWeb.services.EstoqueServices;
import br.com.retaguardaWeb.services.IngredienteService;
import br.com.retaguardaWeb.util.EstoqueAtualUtil;

@Named
@ViewScoped
public class manterEstoqueMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private IngredienteService ingredienteService;
	@EJB
	private EstoqueAtualServices estadoAtualService;
	@EJB
	private EstoqueServices estoqueService;
	
	private MinimoEstoque minEstoque;
	
	EstadoEstoque estadoEstoque = new EstadoEstoque();

	private List <Ingrediente> ingredienteCache ;
	private List <Estoque> listaEstoque ;
	private Estoque estoque = new Estoque();

	private List <MinimoEstoque> minimoEstoque ;

	private List<EstoqueAtualUtil> listaEstoqueAtualUtil = new ArrayList<EstoqueAtualUtil>();
	
	private Long idEstoque;
	private Long idIngrediente;
	HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	
	
	 DecimalFormat fmt = new DecimalFormat("0.000");        
	
	
	public void atualizaEstoque(){
		
        String[] listaQuantidade = request.getParameterValues("listaQuatidade");
		
		int i=0;
		List<EstoqueAtual> estoqueAtual = new ArrayList<EstoqueAtual>();
		for(EstoqueAtualUtil  estoq :  getListaEstoqueAtualUtil()){
			EstoqueAtual estoque = new EstoqueAtual();
			if(listaQuantidade!=null){
				estoque = estoq.getEstoqueAtual();
				estoque.setQuantidade(listaQuantidade[i]);
				estoque.setEstadoEstoque(estadoEstoque);
				System.out.println(listaQuantidade[i]);
				estoque.setId(null);
				if(estoque.getQuantidade()!=null && !estoque.getQuantidade().equals(""))
					estoqueAtual.add(estoque);
			}
			i++;
		}
		estadoEstoque.setId(null);
		estadoEstoque.setListaEstadoEstoque(estoqueAtual);
		estadoEstoque.setDataEstoque(new Timestamp(System.currentTimeMillis()));
		estoque.setId(1L);
		estadoEstoque.setEstoque(estoque);
		estadoAtualService.adiciona(estadoEstoque);
		listaEstadoEstoque();
	}
	
	
	 public void adiciona () {
		 minEstoque.setIdEstoque(getEstoque());
		 this.estoqueService.adicionaIngredienteQtd(minEstoque);
		 minEstoque = new MinimoEstoque();
		 listaIngredientesMinimosPEstoque();
	 }
	
		public void excluir() {
			this.estoqueService.removerIngredienteQtd(getMinEstoque());
			listaIngredientesMinimosPEstoque();
			minEstoque = new MinimoEstoque();
		}
/*	public EstadoEstoque listaEstadoEstoque() {
		if(getIdEstoque()!=null){
			estadoEstoque.getEstoque().setId(getIdEstoque());
			estadoEstoque = estadoAtualService.recuperaEstadoAtual(estadoEstoque);
			estadoEstoque.setListaEstadoEstoque(new ArrayList<EstoqueAtual>());
			if(estadoEstoque.getId()!=null && (estadoEstoque.getListaEstadoEstoque()==null ||estadoEstoque.getListaEstadoEstoque().isEmpty())){
				for(Ingrediente ingrediente : getIngredientes()){
					EstoqueAtual estoque = new EstoqueAtual();
					EstoqueAtual verificaEstoque = new EstoqueAtual();
					estoque.setIdIngrediente(ingrediente);
					estoque.setEstadoEstoque(estadoEstoque);
					verificaEstoque = estadoAtualService.recuperaEstoqueAtual(estoque);
					if(verificaEstoque!=null && verificaEstoque.getId()!=null){
						estoque = verificaEstoque;
					}
					estadoEstoque.getListaEstadoEstoque().add(estoque);
				}
				
			}
		}
		return estadoEstoque;
	}
*/	
	public EstadoEstoque listaEstadoEstoque() {
		listaEstoqueAtualUtil = new ArrayList<EstoqueAtualUtil>();
		if(getIdEstoque()!=null){
			minimoEstoque = estoqueService.recuperaPorId(getEstoque());
			estadoEstoque.getEstoque().setId(getIdEstoque());
			estadoEstoque = estadoAtualService.recuperaEstadoAtual(estadoEstoque);
			for(MinimoEstoque ing : minimoEstoque){
				EstoqueAtualUtil util = new EstoqueAtualUtil();
				
				EstoqueAtual estoque = new EstoqueAtual();
				EstoqueAtual verificaEstoque = new EstoqueAtual();
				estoque.setIdIngrediente(ing.getIngrediente());
				estoque.setEstadoEstoque(estadoEstoque);
				verificaEstoque = estadoAtualService.recuperaEstoqueAtual(estoque);
				if(verificaEstoque!=null && verificaEstoque.getId()!=null){
					estoque = verificaEstoque;
				}
				
				util.setListaIngrediente(ing);
				util.setEstadoEstoque(estadoEstoque);
				util.setEstoqueAtual(estoque);
				double atual=0;
				double minimo=0;
				String recomendado=null;
				if(util.getListaIngrediente().getQuantidade()!=null && util.getEstoqueAtual().getQuantidade()!=null){
					minimo = Double.parseDouble(util.getListaIngrediente().getQuantidade().replaceAll( "," , "." ));
					atual = Double.parseDouble(util.getEstoqueAtual().getQuantidade().replaceAll( "," , "." ));
					double result = minimo-atual;
					if(result>0){
						recomendado =  fmt.format(result);
					}else{
						recomendado = "";
					}
				}
				util.setQtdReposicao(recomendado);
				listaEstoqueAtualUtil.add(util);
			}
			
			
			
		}
		return estadoEstoque;
	}
	
	
	public void listaIngredientesMinimosPEstoque() {
		if(getIdEstoque()!=null && getIdEstoque()>0){
			estoque.setId(getIdEstoque());
			setMinimoEstoque(estoqueService.recuperaPorId(estoque));
		}
	}
	
	public EstadoEstoque getEstadoEstoque() {
		return estadoEstoque;
	}

	public void pegaIdEstoque(){
		estoque.setId(getIdEstoque());
		estadoEstoque.setEstoque(estoque);
	}

	public void setEstadoEstoque(EstadoEstoque estadoEstoque) {
		this.estadoEstoque = estadoEstoque;
	}

	
	public List <Ingrediente> getIngredientes() {
		 if (this.ingredienteCache == null) {
			 this.ingredienteCache = this.ingredienteService.getIngredientes();
		 }
		 return this.ingredienteCache ;
	 }

	public List<Estoque> getListaEstoque() {
		return (List<Estoque>) estoqueService.listaEstoque();
	}

	public void setListaEstoque(List<Estoque> listaEstoque) {
		this.listaEstoque = listaEstoque;
	}

	public Long getIdEstoque() {
		if(idEstoque!=null){
			estoque.setId(idEstoque);
			estadoEstoque.setEstoque(estoque);
		}
		return idEstoque;
	}

	public void setIdEstoque(Long idEstoque) {
		this.idEstoque = idEstoque;
	}

	public Estoque getEstoque() {
		return estoque;
	}

	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}

	public List<MinimoEstoque> getMinimoEstoque() {
		return minimoEstoque;
	}

	public void setMinimoEstoque(List<MinimoEstoque> minimoEstoque) {
		this.minimoEstoque = minimoEstoque;
	}

	public MinimoEstoque getMinEstoque() {
		if(minEstoque==null)
			minEstoque = new MinimoEstoque();
		return minEstoque;
	}

	public void setMinEstoque(MinimoEstoque minEstoque) {
		this.minEstoque = minEstoque;
	}


	public Long getIdIngrediente() {
		if(idIngrediente!=null)
			minEstoque.getIngrediente().setId(idIngrediente);

		return idIngrediente;
	}


	public void setIdIngrediente(Long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}


	public List<EstoqueAtualUtil> getListaEstoqueAtualUtil() {
		return listaEstoqueAtualUtil;
	}


	public void setListaEstoqueAtualUtil(
			List<EstoqueAtualUtil> listaEstoqueAtualUtil) {
		this.listaEstoqueAtualUtil = listaEstoqueAtualUtil;
	}




	
}
