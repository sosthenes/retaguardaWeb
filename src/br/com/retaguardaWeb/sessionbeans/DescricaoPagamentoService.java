package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.DescricaoPagamento;

@Stateless
public class DescricaoPagamentoService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(DescricaoPagamento descricaoPagamento) {
		this.manager.merge(descricaoPagamento);
	}
	
	public void remover(DescricaoPagamento descricaoPagamento) {
		this.manager.remove(descricaoPagamento);
	}

	public DescricaoPagamento pesquisaPorId(DescricaoPagamento descricaoPagamento) {
		return this.manager.find(DescricaoPagamento.class, descricaoPagamento);
	}

	public void alterar(DescricaoPagamento descricaoPagamento) {
		this.manager.merge(descricaoPagamento);
	}

	
	

	public List<DescricaoPagamento> listar(Loja loja) {
		List<DescricaoPagamento> lista = new ArrayList<DescricaoPagamento>();
		String lcQuery = "select x from DescricaoPagamento x "
				+ " where x.idLoja = :loja"
				+ " order by x.descricaogasto";
		TypedQuery<DescricaoPagamento> query = this.manager.createQuery(lcQuery, DescricaoPagamento.class);
		query.setParameter("loja", loja);
		lista = query.getResultList();
		return lista;
	}
	
	public List<DescricaoPagamento> listarPorId(Pagamento pagamento) {
		List<DescricaoPagamento> lista = new ArrayList<DescricaoPagamento>();
		String lcQuery = "select x from DescricaoPagamento x "
				+ " where x.pagamento=:pagamento";
		lcQuery+=  " order by x.numParcela ";
		TypedQuery<DescricaoPagamento> query = this.manager.createQuery(lcQuery, DescricaoPagamento.class);
		query.setParameter("pagamento", pagamento);
		lista = query.getResultList();
		return lista;
	}

	public DescricaoPagamento adicionaParcela(DescricaoPagamento parcela) {
		return manager.merge(parcela);
	}

	public List<DescricaoPagamento> pesquisa(Pagamento conta, Date fim, Date inicio) {
		List<DescricaoPagamento> lista = new ArrayList<DescricaoPagamento>();
		String lcQuery = "select x from DescricaoPagamento x where 1=1";
		
		if(conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			lcQuery+= " and x.pagamento.tipoPagamento=:tipoPagamento";
		
		if(conta.getPeriodocidade()!=null && conta.getPeriodocidade().getId()!=null)
			lcQuery+= " and x.pagamento.periodocidade=:periodocidade";
		
		if(inicio!=null)
			lcQuery+= " and x.dataParcela>=:dataInicio";
		
		if(fim!=null)
			lcQuery+= " and x.dataParcela<=:dataFim";
		
		
		
		lcQuery+=  " and x.pago=false"
				+ ""
				+ " order by x.numParcela ";
		TypedQuery<DescricaoPagamento> query = this.manager.createQuery(lcQuery, DescricaoPagamento.class);
		
		if(conta.getTipoPagamento()!=null && conta.getTipoPagamento().getId()!=null)
			query.setParameter("tipoPagamento", conta.getTipoPagamento());
		
		if(conta.getPeriodocidade()!=null && conta.getPeriodocidade().getId()!=null)
			query.setParameter("periodocidade", conta.getPeriodocidade());
		
		if(inicio!=null)
			query.setParameter("dataInicio", inicio);
		
		if(fim!=null)
			query.setParameter("dataFim", fim);
		
		lista = query.getResultList();
		return lista;
	}
}
