package br.com.retaguardaWeb.sessionbeans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Fechamentocaixa;
import br.com.retaguardaWeb.entidades.MinimoEstoque;

@Stateless
public class FechamentoCaixaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Fechamentocaixa fechamentocaixa) {
		this.manager.merge(fechamentocaixa);
	}
	
	public void remover(Fechamentocaixa fechamentocaixa) {
		fechamentocaixa = manager.merge(fechamentocaixa);
		this.manager.remove(fechamentocaixa);
	}

	public void alterar(Fechamentocaixa fechamentocaixa) {
		this.manager.merge(fechamentocaixa);
	}

	
	
	public List<Fechamentocaixa> getFechamentocaixas() {
		TypedQuery<Fechamentocaixa> query = this.manager.createQuery(
				"select x from Fechamentocaixa x", Fechamentocaixa.class);

		return query.getResultList();
	}

	public Fechamentocaixa fechamentocaixaPorId(Fechamentocaixa ingred) {
		return this.manager.find(Fechamentocaixa.class, ingred);
	}

	public List<Fechamentocaixa> fechamentoPorCaixa(Caixa caixa) {
		List<Fechamentocaixa> lista = new ArrayList<Fechamentocaixa>();
		String lcQuery = "select x from Fechamentocaixa x ";
		lcQuery+= " where date(x.data)=date(:data)";
		TypedQuery<Fechamentocaixa> query = this.manager.createQuery(lcQuery, Fechamentocaixa.class);
		query.setParameter("data", new Timestamp(System.currentTimeMillis()));
		lista = query.getResultList();
		return lista;
	}

	public List<Fechamentocaixa> getFechamentoCaixasPorDataInicioFim(
			Date dataInicio, Date dataFim) {
		List<Fechamentocaixa> lista = new ArrayList<Fechamentocaixa>();
		String lcQuery = "select x from Fechamentocaixa x ";
		lcQuery+= " where date(x.data)>=date(:dataInicio)"
				+ " and date(x.data)<=date(:dataFim)";
		TypedQuery<Fechamentocaixa> query = this.manager.createQuery(lcQuery, Fechamentocaixa.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		lista = query.getResultList();
		return lista;
	}

	public Fechamentocaixa fechamentocaixaPorCaixa(Date dataInicio, Caixa caixa) {
		
		try {
			Fechamentocaixa lista = new Fechamentocaixa();
			String lcQuery = "select x from Fechamentocaixa x ";
			lcQuery+= " where date(x.data)=date(:dataInicio)"
					+ " and x.caixa=:caixa";
			TypedQuery<Fechamentocaixa> query = this.manager.createQuery(lcQuery, Fechamentocaixa.class);
			query.setParameter("dataInicio", dataInicio);
			query.setParameter("caixa", caixa);
			lista = (Fechamentocaixa)query.getSingleResult()==null?null:query.getSingleResult();
			return lista;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

/*	public List<Fechamentocaixa> relatorioListaGasto(FechamentocaixaVO gastoVO) {
		List<Fechamentocaixa> lista = new ArrayList<Fechamentocaixa>();
		String lcQuery = "select x from Fechamentocaixa x where DATE_FORMAT(x.dataHora, '%d/%m/%Y')=:data";
		TypedQuery<Fechamentocaixa> query = this.manager.createQuery(lcQuery, Fechamentocaixa.class);
		query.setParameter("data", gastoVO.getDatafechamentocaixa());
		lista = query.getResultList();
		return lista;
	}*/
	

}
