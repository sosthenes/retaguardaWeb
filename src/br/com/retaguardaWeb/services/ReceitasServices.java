package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Produto;
import br.com.retaguardaWeb.entidades.ReceitaProduto;

@Stateless
public class ReceitasServices {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(ReceitaProduto receiReceitaProduto) {
		this.manager.merge(receiReceitaProduto);
	}
	
	public void remover(ReceitaProduto receiReceitaProduto) {
		receiReceitaProduto = manager.merge(receiReceitaProduto);
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

	public List<ReceitaProduto> listaReceitaProdudo(Produto produto) {
		TypedQuery<ReceitaProduto> query = this.manager.createQuery(
				"select x from ReceitaProduto x where x.idProduto=:idProduto", ReceitaProduto.class);
		query.setParameter("idProduto", produto);
		return query.getResultList();
	}
}
