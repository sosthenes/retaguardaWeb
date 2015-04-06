package br.com.retaguardaWeb.sessionbeans;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.MesaLoja;

@Stateless
public class MesaLojaEJB {

	@PersistenceContext
	private EntityManager manager;
	
	public void adiciona(MesaLoja mesaLoja) {
		this.manager.persist(mesaLoja);
	}
	
	public void remover(MesaLoja mesaLoja) {
		 manager.remove(manager.getReference(MesaLoja.class, mesaLoja.getId())); 
	}

	public void alterar(MesaLoja mesaLoja) {
		this.manager.merge(mesaLoja);
	}

	
	
	public List<MesaLoja> getMesaLojas(Loja loja) {
		TypedQuery<MesaLoja> query = this.manager.createQuery(
				"select x from MesaLoja x"
				+ " where x.idLoja=:idLoja"
				+ " order by descricao", MesaLoja.class);
		query.setParameter("idLoja", loja);
		return query.getResultList();
	}

	public MesaLoja mesaLojaPorId(MesaLoja ingred) {
		return this.manager.find(MesaLoja.class, ingred);
	}

	
}
