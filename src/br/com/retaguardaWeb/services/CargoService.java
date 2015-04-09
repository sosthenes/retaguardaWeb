package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Cargo;

@Stateless
public class CargoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Cargo cargo) {
		this.manager.merge(cargo);
	}

	public void remover(Cargo cargo) {
		cargo = this.manager.merge(cargo);
		this.manager.remove(cargo);
	}

	
	public List<Cargo> getCargos() {
		List<Cargo> listacargos = new ArrayList<Cargo>();
		TypedQuery<Cargo> query = this.manager.createQuery("select x from Cargo x", Cargo.class);
		try {
			listacargos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listacargos;
	}


	public Cargo pesquisaPorId(Cargo cargo) {
		TypedQuery<Cargo> query = this.manager.createQuery("select x from Cargo x where idCargo = :id", Cargo.class);
		query.setParameter("id", cargo.getId());
		return query.getSingleResult();
	}

	public List<Cargo> listaCargoPorLoja(Loja loja) {
		List<Cargo> listacargos = new ArrayList<Cargo>();
		TypedQuery<Cargo> query = this.manager.createQuery("select x from Cargo x where x.idLoja=:loja", Cargo.class);
		try {
			query.setParameter("loja", loja);
			listacargos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listacargos;
	}
}
