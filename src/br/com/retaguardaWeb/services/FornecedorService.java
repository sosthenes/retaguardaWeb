package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Fornecedor;

@Stateless
public class FornecedorService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Fornecedor fornecedor) {
		this.manager.persist(fornecedor);
	}
	
	public void remover(Fornecedor fornecedor) {
		this.manager.remove(fornecedor);
	}

	public void alterar(Fornecedor fornecedor) {
		this.manager.merge(fornecedor);
	}

	
	
	public List<Fornecedor> getFornecedores() {
		TypedQuery<Fornecedor> query = this.manager.createQuery(
				"select x from Fornecedor x", Fornecedor.class);

		return query.getResultList();
	}
}
