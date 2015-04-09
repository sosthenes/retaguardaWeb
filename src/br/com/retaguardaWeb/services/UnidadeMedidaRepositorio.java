package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Ingrediente;
import br.com.retaguardaWeb.entidades.UnidadeMedida;

@Stateless
public class UnidadeMedidaRepositorio {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(UnidadeMedida unidadeMedida) {
		this.manager.persist(unidadeMedida);
	}

	public List<UnidadeMedida> getIUnidadeMedidas() {
		TypedQuery<UnidadeMedida> query = this.manager.createQuery("select x from UnidadeMedida x", UnidadeMedida.class);

		return query.getResultList();
	}
}
