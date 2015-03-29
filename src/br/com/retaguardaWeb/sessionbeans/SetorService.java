package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Setor;

@Stateless
public class SetorService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Setor setor) {
		this.manager.merge(setor);
	}

	public void remover(Setor setor) {
		setor = this.manager.merge(setor);
		this.manager.remove(setor);
	}

	
	public List<Setor> getSetors() {
		List<Setor> listasetors = new ArrayList<Setor>();
		TypedQuery<Setor> query = this.manager.createQuery("select x from Setor x", Setor.class);
		try {
			listasetors = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listasetors;
	}


	public Setor pesquisaPorId(Setor setor) {
		TypedQuery<Setor> query = this.manager.createQuery("select x from Setor x where idSetor = :id", Setor.class);
		query.setParameter("id", setor.getId());
		return query.getSingleResult();
	}

	public List<Setor> listaSetorPorLoja(Loja loja) {
		List<Setor> listasetors = new ArrayList<Setor>();
		TypedQuery<Setor> query = this.manager.createQuery("select x from Setor x where x.idLoja=:loja", Setor.class);
		try {
			query.setParameter("loja", loja);
			listasetors = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listasetors;
	}
}
