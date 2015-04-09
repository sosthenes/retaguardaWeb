package br.com.retaguardaWeb.services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;

@Stateless
public class PeriodoTrabalhoService {

	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private CaixaService caixaService;

	public void adiciona(PeriodoTrabalho periodoTrabalho) {
		this.manager.persist(periodoTrabalho);
	}
	
	public void remover(PeriodoTrabalho periodoTrabalho) {
		this.manager.remove(periodoTrabalho);
	}

	public void alterar(PeriodoTrabalho periodoTrabalho) {
		this.manager.merge(periodoTrabalho);
	}

	
	
	public List<PeriodoTrabalho> getPeriodoTrabalhoes() {
		TypedQuery<PeriodoTrabalho> query = this.manager.createQuery(
				"select x from PeriodoTrabalho x", PeriodoTrabalho.class);

		return query.getResultList();
	}

	public PeriodoTrabalho recuperaUltimoPeriodo(Loja loja) {
		PeriodoTrabalho periodoTrabalho = new PeriodoTrabalho();
		try {
			TypedQuery<PeriodoTrabalho> query = this.manager.createQuery("select max(x) from PeriodoTrabalho x"
					+ " where x.idLoja=:loja"
					+ " order by x.horaInicio desc", PeriodoTrabalho.class);
			
			query.setParameter("loja", loja);
			periodoTrabalho = query.getSingleResult();
		} catch (Exception e) {
		return null;
		}
		return periodoTrabalho;
	}

	public boolean verificaCaixaAberto(Loja loja, PeriodoTrabalho periodo, Funcionario funcionario) {
		return caixaService.verificaCaixaAberto(loja, periodo, funcionario);
	}
}
