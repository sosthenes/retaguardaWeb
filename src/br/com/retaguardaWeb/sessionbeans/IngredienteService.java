package br.com.retaguardaWeb.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Ingrediente;

@Stateless
public class IngredienteService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Ingrediente ingrediente) {
		this.manager.persist(ingrediente);
	}
	
	public void remover(Ingrediente ingrediente) {
		this.manager.remove(ingrediente);
	}

	public void alterar(Ingrediente ingrediente) {
		this.manager.merge(ingrediente);
	}

	
	
	public List<Ingrediente> getIngredientes() {
		TypedQuery<Ingrediente> query = this.manager.createQuery(
				"select x from Ingrediente x", Ingrediente.class);

		return query.getResultList();
	}

	public Ingrediente ingredientePorId(Ingrediente ingred) {
		return this.manager.find(Ingrediente.class, ingred);
	}
}
