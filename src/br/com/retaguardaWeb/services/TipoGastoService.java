package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.TipoGasto;

@Stateless
public class TipoGastoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(TipoGasto tipoGasto) {
		this.manager.merge(tipoGasto);
	}
	
	public void remover(TipoGasto tipoGasto) {
		tipoGasto = this.manager.merge(tipoGasto);
		this.manager.remove(tipoGasto);
	}

	public void alterar(TipoGasto tipoGasto) {
		this.manager.merge(tipoGasto);
	}

	
	
	public List<TipoGasto> getTipoGastos(Loja loja) {
		TypedQuery<TipoGasto> query = this.manager.createQuery(
				"select x from TipoGasto x where x.idLoja=:loja", TipoGasto.class);
		query.setParameter("loja", loja);
		return query.getResultList();
	}

	public TipoGasto tipoGastoPorId(TipoGasto ingred) {
		return this.manager.find(TipoGasto.class, ingred);
	}
}
