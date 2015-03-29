package br.com.retaguardaWeb.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Loja;

@Stateless
public class CaixaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Caixa caixa) {
		this.manager.merge(caixa);
	}
	
	public void remover(Caixa caixa) {
		this.manager.remove(caixa);
	}

	public void alterar(Caixa caixa) {
		this.manager.merge(caixa);
	}

	
	
	public List<Caixa> getCaixas(Loja loja) {
		TypedQuery<Caixa> query = this.manager.createQuery(
				"select x from Caixa x where x.idLoja=:loja", Caixa.class);
		query.setParameter("loja", loja);
		return query.getResultList();
	}

	public Caixa caixaPorId(Caixa ingred) {
		return this.manager.find(Caixa.class, ingred);
	}
}
