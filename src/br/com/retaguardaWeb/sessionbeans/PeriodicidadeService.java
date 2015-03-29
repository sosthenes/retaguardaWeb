package br.com.retaguardaWeb.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Periodicidade;

@Stateless
public class PeriodicidadeService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Periodicidade Periodicidade) {
		this.manager.merge(Periodicidade);
	}
	
	public void remover(Periodicidade Periodicidade) {
		this.manager.remove(Periodicidade);
	}

	public void alterar(Periodicidade Periodicidade) {
		this.manager.merge(Periodicidade);
	}

	
	
	public List<Periodicidade> getPeriodicidades() {
		TypedQuery<Periodicidade> query = this.manager.createQuery(
				"select x from Periodicidade x", Periodicidade.class);

		return query.getResultList();
	}

	public Periodicidade PeriodicidadePorId(Periodicidade ingred) {
		return this.manager.find(Periodicidade.class, ingred.getId());
	}
}
