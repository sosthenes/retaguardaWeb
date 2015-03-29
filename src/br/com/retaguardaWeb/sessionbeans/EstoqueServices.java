package br.com.retaguardaWeb.sessionbeans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.Estoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.MinimoEstoque;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.ReceitaProduto;
import br.com.retaguardaWeb.entidades.RetiradaEstoque;
import br.com.retaguardaWeb.util.EstoqueAtualUtil;
import br.com.retaguardaWeb.util.IngredienteQuantidade;

@Stateless
public class EstoqueServices{

	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private EstoqueAtualServices estadoAtualService;
	
	@EJB
	ReceitasServices receitasServices;
	
	DecimalFormat fmt = new DecimalFormat("0.000"); 

	public void adiciona(ReceitaProduto receiReceitaProduto) {
		this.manager.persist(receiReceitaProduto);
	}
	
	public void remover(ReceitaProduto receiReceitaProduto) {
		this.manager.remove(receiReceitaProduto);
	}

	public void alterar(ReceitaProduto receiReceitaProduto) {
		this.manager.merge(receiReceitaProduto);
	}

	public void retiraIngredientesEstoque(Produto produto, String qtd, EstadoEstoque estadoEstoque, List<IngredienteQuantidade> listaQuantidadeIngrediente){
		estadoEstoque.getEstoque().setId(1L);
		estadoEstoque = estadoAtualService.recuperaEstadoAtual(estadoEstoque);
		for(ReceitaProduto receita : receitasServices.listaReceitaProdudo(produto)){
			EstoqueAtual estoque = new EstoqueAtual();
			EstoqueAtual verificaEstoque = new EstoqueAtual();
			estoque.setIdIngrediente(receita.getIdIngrediente());
			estoque.setEstadoEstoque(estadoEstoque);
			estoque.setId(1L);
			estoque.setEstadoEstoque(estadoEstoque);
			verificaEstoque = estadoAtualService.recuperaEstoqueAtual(estoque);
			if(verificaEstoque==null){
				verificaEstoque = new EstoqueAtual() ;
			}
			estoque.setQuantidade(cauculaRetiradaEstoque(verificaEstoque.getQuantidade(), receita.getQuantidade(), qtd));

			IngredienteQuantidade ingQtd = new IngredienteQuantidade();
			boolean existe = false;
			int b=0;
			for(IngredienteQuantidade igt :listaQuantidadeIngrediente) {
				if(igt.getIngrediente().getNome().equals(receita.getIdIngrediente().getNome())){
					igt.setQtd(igt.getQtd()-(Double.parseDouble(receita.getQuantidade().replaceAll(",", "."))*Double.parseDouble(qtd)));
					listaQuantidadeIngrediente.get(b).setQtd(igt.getQtd());
					listaQuantidadeIngrediente.get(b).setQuantidadeRetirada((Double.parseDouble(receita.getQuantidade().replaceAll(",", "."))*Double.parseDouble(qtd)));
					existe = true;
				}
				b++;
			}
			if(!existe){
				ingQtd.setIngrediente(receita.getIdIngrediente());
				ingQtd.setQtd(Double.parseDouble(estoque.getQuantidade().replaceAll(",", ".")));
				listaQuantidadeIngrediente.add(ingQtd);
				listaQuantidadeIngrediente.get(b).setQuantidadeRetirada((Double.parseDouble(receita.getQuantidade().replaceAll(",", "."))*Double.parseDouble(qtd)));
			}
			estoque.setEstadoEstoque(estadoEstoque);
			estoque.setId(null);
			
			
		}
		
		
	}

	public String cauculaRetiradaEstoque(String qtdEstoque, String qtdRetirada, String qtdARetirar){
		String resultado = "0";
		if(qtdARetirar!=null && !qtdARetirar.equals("0") && !qtdARetirar.equals("")){
			double calc = Double.parseDouble(qtdRetirada==null||qtdRetirada.equals("")?"0":qtdRetirada) * Double.parseDouble(qtdARetirar==null||qtdARetirar.equals("")?"0":qtdARetirar);
			resultado = new DecimalFormat("0.000").format(Double.parseDouble(qtdEstoque.replaceAll(",", ".")==null||qtdEstoque.replaceAll(",", ".").equals("")?"0":qtdEstoque.replaceAll(",", ".")) - calc) ;
		}
		return resultado;
	}
	
	public List<ReceitaProduto> getReceitaProdutos() {
		TypedQuery<ReceitaProduto> query = this.manager.createQuery(
				"select x from ReceitaProduto x", ReceitaProduto.class);

		return query.getResultList();
	}

	public List<Estoque> listaEstoque() {
		TypedQuery<Estoque> query = this.manager.createQuery(
				"select x from Estoque x ", Estoque.class);
		return query.getResultList();
	}

	public List<MinimoEstoque> recuperaPorId(Estoque estoque) {
		List<MinimoEstoque> estoqueAtual = new ArrayList<MinimoEstoque>();
		TypedQuery<MinimoEstoque> query = this.manager.createQuery("select x from MinimoEstoque x where x.idEstoque = :estoque", MinimoEstoque.class);
		query.setParameter("estoque", estoque);
		try {
			estoqueAtual = (List<MinimoEstoque>) query.getResultList();
			return estoqueAtual;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void adicionaIngredienteQtd(MinimoEstoque minEstoque) {
		this.manager.merge(minEstoque);
	}

	public void removerIngredienteQtd(MinimoEstoque minEstoque) {
		minEstoque = manager.merge(minEstoque);
		this.manager.remove(minEstoque);
	}

	public void salvaRetirada(RetiradaEstoque retirada) {
		this.manager.merge(retirada);
	}

	public void salvaRetiradas(List<EstoqueAtualUtil> listaEstoqueAtualUtil, List<IngredienteQuantidade> listaQuantidadeIngrediente, DecimalFormat fmt, List<EstoqueAtual> estoqueAtual) {
		int i=0;
		for(EstoqueAtualUtil est : listaEstoqueAtualUtil){
			boolean teste = false;
			RetiradaEstoque retirada ;
			for(IngredienteQuantidade in : listaQuantidadeIngrediente){
				retirada = new RetiradaEstoque();
				if(in.getIngrediente().getId().equals(est.getEstoqueAtual().getIdIngrediente().getId())){
					EstoqueAtual ea = new EstoqueAtual();
					ea = est.getEstoqueAtual();
					ea.setQuantidade(fmt.format(in.getQtd()));
					estoqueAtual.add(ea);
					populaRetirada(retirada, ea, in);
					salvaRetirada(retirada);
					teste = true;
				}
			}
			if(!teste){
				EstoqueAtual ea = new EstoqueAtual();
				ea = est.getEstoqueAtual();
				estoqueAtual.add(ea);
				retirada = new RetiradaEstoque();
				populaRetiradaZerada(retirada, ea);
				salvaRetirada(retirada);
			}
				i++;
		}
		
	}
	
	
	private void populaRetirada(RetiradaEstoque retirada, EstoqueAtual ea, IngredienteQuantidade in) {
		retirada.setEstadoEstoque(ea.getEstadoEstoque());
		retirada.setIdIngrediente(ea.getIdIngrediente());
		retirada.setQuantidade(fmt.format(in.getQuantidadeRetirada()));
	}

	private void populaRetiradaZerada(RetiradaEstoque retirada, EstoqueAtual ea) {
		retirada.setEstadoEstoque(ea.getEstadoEstoque());
		retirada.setIdIngrediente(ea.getIdIngrediente());
		retirada.setQuantidade("0");
	}

	public EstadoEstoque listaEstadoEstoque(List<EstoqueAtualUtil> listaEstoqueAtualUtil, Estoque estoque2, List<MinimoEstoque> minimoEstoque, EstadoEstoque estadoEstoque) {
		listaEstoqueAtualUtil = new ArrayList<EstoqueAtualUtil>();
		if(new Long(1)!=null){
			estoque2.setId(1L);
			minimoEstoque = recuperaPorId(estoque2);
			estadoEstoque.getEstoque().setId(new Long(1));
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
				String recomendado=null;
				util.setQtdReposicao(recomendado);
				listaEstoqueAtualUtil.add(util);
			}
			
			
			
		}
		return estadoEstoque;
	}
	
	
}
