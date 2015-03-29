package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.CategoriaProduto;
import br.com.retaguardaWeb.entidades.Produto;

@Stateless
public class ProdutoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Produto produto) {
		this.manager.persist(produto);
	}

	public List<Produto> getProdutos() {
		List<Produto> listaprodutos = new ArrayList<Produto>();
		TypedQuery<Produto> query = this.manager.createQuery("select x from Produto x", Produto.class);
		try {
			listaprodutos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaprodutos;
	}

	public List<Produto> listaProdudo(CategoriaProduto idCategoria) {
		List<Produto> listaprodutos = new ArrayList<Produto>();
		TypedQuery<Produto> query = this.manager.createQuery("select x from Produto x where x.categoria = :idCategoria", Produto.class);
		query.setParameter("idCategoria", idCategoria);
		try {
			listaprodutos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaprodutos;
	}

	public Produto pesquisa(Produto produto) {
		TypedQuery<Produto> query = this.manager.createQuery("select x from Produto x where idProduto = :id", Produto.class);
		query.setParameter("id", produto.getId());
		return query.getSingleResult();
	}

	public List<Produto> recuperaListaProdutoVendido(Date dataInicio, Date dataFim) {
		List<Produto> listaprodutos = new ArrayList<Produto>();
		TypedQuery<Produto> query = this.manager.createQuery(
				"select x from Produto x where x.categoria = :idCategoria", Produto.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		try {
			listaprodutos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaprodutos;
	}
}
