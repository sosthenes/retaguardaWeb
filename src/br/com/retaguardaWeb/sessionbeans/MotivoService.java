package br.com.retaguardaWeb.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Motivo;

@Stateless
public class MotivoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Motivo motivo) {
		this.manager.merge(motivo);
	}
	
	public void remover(Motivo motivo) {
		this.manager.remove(motivo);
	}

	public void alterar(Motivo motivo) {
		this.manager.merge(motivo);
	}

	
	
	public List<Motivo> getMotivos() {
		TypedQuery<Motivo> query = this.manager.createQuery(
				"select x from Motivo x", Motivo.class);

		return query.getResultList();
	}

	public Motivo motivoPorId(Motivo idMotivo) {
		return this.manager.find(Motivo.class, idMotivo);
	}
}
