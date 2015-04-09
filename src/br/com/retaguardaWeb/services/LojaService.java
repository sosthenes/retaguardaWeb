package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;

@Stateless
public class LojaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Loja loja) {
		this.manager.merge(loja);
	}

	public void remover(Loja loja) {
		loja = this.manager.merge(loja);
		this.manager.remove(loja);
	}

	
	public List<Loja> getLojas() {
		List<Loja> listalojas = new ArrayList<Loja>();
		TypedQuery<Loja> query = this.manager.createQuery("select x from Loja x order by x.descricao", Loja.class);
		try {
			listalojas = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listalojas;
	}


	public Loja pesquisaPorId(Loja loja) {
		TypedQuery<Loja> query = this.manager.createQuery("select x from Loja x where idLoja = :id", Loja.class);
		query.setParameter("id", loja.getId());
		return query.getSingleResult();
	}
}
