package br.com.retaguardaWeb.sessionbeans;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.ReceitaProduto;

@Stateless
public class EstoqueAtualServices {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(EstadoEstoque estadoEstoque) {
		this.manager.merge(estadoEstoque);
	}
	
	public void remover(ReceitaProduto receiReceitaProduto) {
		this.manager.remove(receiReceitaProduto);
	}

	public void alterar(ReceitaProduto receiReceitaProduto) {
		this.manager.merge(receiReceitaProduto);
	}

	
	
	public List<ReceitaProduto> getReceitaProdutos() {
		TypedQuery<ReceitaProduto> query = this.manager.createQuery(
				"select x from ReceitaProduto x", ReceitaProduto.class);

		return query.getResultList();
	}
	
	 

	public EstoqueAtual recuperaEstoqueAtual(EstoqueAtual estoqueAtual) {

		TypedQuery<EstoqueAtual> query = this.manager.createQuery(
				"select x from EstoqueAtual x where x.idIngrediente=:idIngrediente and x.estadoEstoque= :idestadoEstoque", EstoqueAtual.class);
		query.setParameter("idIngrediente", estoqueAtual.getIdIngrediente());
		query.setParameter("idestadoEstoque", estoqueAtual.getEstadoEstoque());
		query.setMaxResults(1);
		try {
			return (EstoqueAtual)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<EstoqueAtual> recuperaListaEstoqueAtual(EstadoEstoque estadoAtual) {

		TypedQuery<EstoqueAtual> query = this.manager.createQuery(
				"select x from EstoqueAtual x where x.estadoEstoque= :idestadoEstoque", EstoqueAtual.class);
		query.setParameter("idestadoEstoque", estadoAtual);
		try {
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	public List<ReceitaProduto> listaReceitaProdudo(Produto produto) {
		TypedQuery<ReceitaProduto> query = this.manager.createQuery(
				"select x from ReceitaProduto x where x.idProduto=:idProduto", ReceitaProduto.class);
		query.setParameter("idProduto", produto);
		return query.getResultList();
	}

	public EstadoEstoque recuperaEstadoAtual(EstadoEstoque estadoEstoque) {
		try {
			TypedQuery<EstadoEstoque> query = this.manager.createQuery("select x from EstadoEstoque x where x.estoque = :idEstoque order by x.id desc", EstadoEstoque.class);
			query.setParameter("idEstoque", estadoEstoque.getEstoque());
			query.setMaxResults(1);
			return query.getSingleResult();
		} catch (Exception e) {
			return estadoEstoque;
		}
	}
}
