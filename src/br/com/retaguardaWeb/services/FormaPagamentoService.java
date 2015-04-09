package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.FormaPagamento;

@Stateless
public class FormaPagamentoService {

	
	@PersistenceContext
	private EntityManager manager;
	

	
	public List<FormaPagamento> listaFormaPagamento() {
		TypedQuery<FormaPagamento> query = this.manager.createQuery(
				"select x from FormaPagamento x order by x.descricao", FormaPagamento.class);

		return query.getResultList();
	}

	public FormaPagamento TipoDePagamentoPorId(FormaPagamento ingred) {
		return this.manager.find(FormaPagamento.class, ingred);
	}

}
