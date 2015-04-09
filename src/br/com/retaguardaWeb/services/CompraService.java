package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Compras;

@Stateless
public class CompraService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Compras compras) {
		this.manager.merge(compras);
	}

	public void remover(Compras compras) {
		compras = this.manager.merge(compras);
		this.manager.remove(compras);
	}

	
	public List<Compras> getComprass() {
		List<Compras> listacompras = new ArrayList<Compras>();
		TypedQuery<Compras> query = this.manager.createQuery("select x from Compras x", Compras.class);
		try {
			listacompras = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listacompras;
	}


	public Compras pesquisaPorId(Compras compras) {
		TypedQuery<Compras> query = this.manager.createQuery("select x from Compras x where idCompras = :id", Compras.class);
		query.setParameter("id", compras.getId());
		return query.getSingleResult();
	}

	public List<Compras> listaComprasPorLoja(Loja loja) {
		List<Compras> listacompras = new ArrayList<Compras>();
		TypedQuery<Compras> query = this.manager.createQuery("select x from Compras x where x.idLoja=:loja", Compras.class);
		try {
			query.setParameter("loja", loja);
			listacompras = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listacompras;
	}
}
