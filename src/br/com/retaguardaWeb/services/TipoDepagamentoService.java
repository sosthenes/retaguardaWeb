package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.TipoDePagamento;

@Stateless
public class TipoDepagamentoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(TipoDePagamento TipoDePagamento) {
		this.manager.merge(TipoDePagamento);
	}
	
	public void remover(TipoDePagamento TipoDePagamento) {
		this.manager.remove(TipoDePagamento);
	}

	public void alterar(TipoDePagamento TipoDePagamento) {
		this.manager.merge(TipoDePagamento);
	}

	
	
	public List<TipoDePagamento> getTipoDePagamentos(Loja loja) {
		TypedQuery<TipoDePagamento> query = this.manager.createQuery(
				"select x from TipoDePagamento x"
				+ " where x.idLoja = :empresa"
				+ " order by x.descricao", TipoDePagamento.class);
				query.setParameter("empresa", loja);
		return query.getResultList();
	}

	public TipoDePagamento TipoDePagamentoPorId(TipoDePagamento ingred) {
		return this.manager.find(TipoDePagamento.class, ingred);
	}
}
