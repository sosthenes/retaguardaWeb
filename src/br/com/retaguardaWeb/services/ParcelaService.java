package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;

@Stateless
public class ParcelaService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(ParcelaPagamento parcelaPagamento) {
		this.manager.merge(parcelaPagamento);
	}
	
	public void remover(ParcelaPagamento parcelaPagamento) {
		this.manager.remove(parcelaPagamento);
	}

	public ParcelaPagamento pesquisaPorId(ParcelaPagamento parcelaPagamento) {
		return this.manager.find(ParcelaPagamento.class, parcelaPagamento);
	}

	public void alterar(ParcelaPagamento parcelaPagamento) {
		this.manager.merge(parcelaPagamento);
	}

	
	

	public List<ParcelaPagamento> listar(Date dataInicio, Date dataFim) {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select distinct x from ParcelaPagamento x where  1=1";
		if(dataInicio!=null ){
			lcQuery+= " and date(x.dataCompra)>=date(:dataInicio)";
		}
		if(dataFim!=null){
			lcQuery+= " and date(x.dataCompra)<=date(:dataFim)";
		}
		lcQuery+=  " order by x.dataCompra desc";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
		if(dataInicio!=null ){
			query.setParameter("dataInicio", dataInicio);
		}
		if(dataFim!=null){
			query.setParameter("dataFim", dataFim);
		}
		lista = query.getResultList();
		return lista;
	}
	
	public List<ParcelaPagamento> listarPorId(Pagamento pagamento) {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select x from ParcelaPagamento x "
				+ " where x.pagamento=:pagamento";
		lcQuery+=  " order by x.numParcela ";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
		query.setParameter("pagamento", pagamento);
		lista = query.getResultList();
		return lista;
	}

	public ParcelaPagamento adicionaParcela(ParcelaPagamento parcela) {
		return manager.merge(parcela);
	}

	public List<ParcelaPagamento> pesquisa(Pagamento conta, Date fim, Date inicio) {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select distinct x from ParcelaPagamento x where 1=1";
		
		if(conta!=null && conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.tipoPagamento=:tipoPagamento";
		
		if(conta!=null && conta.getDescricaoPagamento()!=null && conta.getDescricaoPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.descricaoPagamento=:descricaoPagamento";
		
		if(conta!=null && conta.getPeriodocidade()!=null && conta.getPeriodocidade().getId()!=null)
			lcQuery+= " and x.pagamento.periodocidade=:periodocidade";
		
		if(conta!=null && conta.getFormaPagamento()!=null && conta.getFormaPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.formaPagamento=:formaPagamento";
		
		if(inicio!=null)
			lcQuery+= " and date(x.dataParcela)>=date(:dataInicio)";
		
		if(fim!=null)
			lcQuery+= " and date(x.dataParcela)<=date(:dataFim)";
		
		
		
		/*lcQuery+=  " and x.pago=false"*/
		lcQuery+= ""
				+ " order by  x.pago, x.descricao, x.dataParcela desc ";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
		
		if(conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			query.setParameter("tipoPagamento", conta.getTipoPagamento());
		
		if(conta.getDescricaoPagamento()!=null && conta.getDescricaoPagamento().getId()!=null)
			query.setParameter("descricaoPagamento", conta.getDescricaoPagamento());

		if(conta.getPeriodocidade()!=null && conta.getPeriodocidade().getId()!=null)
			query.setParameter("periodocidade", conta.getPeriodocidade());
		
		if(conta!=null && conta.getFormaPagamento()!=null && conta.getFormaPagamento().getId()!=null)
			query.setParameter("formaPagamento", conta.getFormaPagamento());
		
		if(inicio!=null)
			query.setParameter("dataInicio", inicio);
		
		if(fim!=null)
			query.setParameter("dataFim", fim);
		
		lista = query.getResultList();
		return lista;
	}

	public List<ParcelaPagamento> pesquisaContaAtrasada(Pagamento conta) {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select distinct x from ParcelaPagamento x where 1=1";
		lcQuery += " and date(x.dataParcela) < date(:dataHoje)";
		lcQuery += " and x.pago=false";
		if(conta!=null && conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.tipoPagamento=:tipoPagamento";
		
		if(conta!=null && conta.getDescricaoPagamento()!=null && conta.getDescricaoPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.descricaoPagamento=:descricaoPagamento";
		
		lcQuery += "  order by  x.dataParcela  ";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
		query.setParameter("dataHoje", new Date());
		if(conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			query.setParameter("tipoPagamento", conta.getTipoPagamento());
		
		if(conta.getDescricaoPagamento()!=null && conta.getDescricaoPagamento().getId()!=null)
			query.setParameter("descricaoPagamento", conta.getDescricaoPagamento());
		lista = query.getResultList();
		return lista;
	}

	public boolean verificaQuitacaoPorId(ParcelaPagamento parcela) {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select x from ParcelaPagamento x where x.pagamento=:parcela and x.pago=false";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
		query.setParameter("parcela", parcela.getPagamento());
		lista = query.getResultList();
		return lista.isEmpty();
		
	}
}
