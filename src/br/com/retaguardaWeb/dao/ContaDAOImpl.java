package br.com.retaguardaWeb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.TipoDePagamento;

public class ContaDAOImpl extends GenericDAOImpl<Pagamento> implements ContaDAO {
	
	@Override
	public Pagamento recuperarPorId(Long id) {
		return manager.getReference(Pagamento.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pagamento> pesquisar(Pagamento pagamento) {
		StringBuilder jpql = new StringBuilder();
		jpql.append("select e from Pagamento e ");
		jpql.append("where e.nome like :nome ");
		Query query = manager.createQuery(jpql.toString());
		//query.setParameter("nome", '%' + pagamento.getNome() + '%');
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagamento recuperarPorNome(String nome) {
		String jpql = "select te from Pagamento te "
					+ "where te.nome = :nome ";
		
		Query query = manager.createQuery(jpql);
		query.setParameter("nome", nome);
		List<Pagamento> lista = query.getResultList();
		if (lista == null || lista.isEmpty())
			return null;
		return lista.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pagamento> consultaPagamento(Pagamento pagamento) {
		StringBuilder sql = new StringBuilder();
		sql.append("select e from Pagamento e where 1 = 1");
		
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		/*if(pagamento.getNome()!=null && !pagamento.getNome().equals("")){
			sql.append(" and e.nome like :nome");
			parametros.put("nome", "%"+pagamento.getNome()+"%");
		}if(pagamento.getTipoPagamento()!=null && pagamento.getTipoPagamento().getId()>0){
			sql.append(" and e.tipoPagamento=:tipoPagamento");
			parametros.put("tipoPagamento", pagamento.getTipoPagamento());
		}if(pagamento.getDataInicio()!=null){
			sql.append(" and e.dataInicio>=:dataInicio");
			parametros.put("dataInicio", pagamento.getDataInicio());
		}if(pagamento.getDataFim()!=null){
			sql.append(" and e.dataFim<=:dataFim");
			parametros.put("dataFim", pagamento.getDataFim());
		}if(pagamento.getStatus()!='\0'){
			sql.append(" and e.status=:status");
			parametros.put("status", pagamento.getStatus());
		}if(pagamento.getLocal()!=null && pagamento.getLocal().getId()>0){
			sql.append(" and e.local=:local");
			parametros.put("local", pagamento.getStatus());
		}*/

		List<? extends Object> listaPagamento = executarQueryJPQL(sql.toString(), parametros);
		return (List<Pagamento>)listaPagamento;
		
	}

	@Override
	public List<TipoDePagamento> getTipoDePagamentos() {
		TypedQuery<TipoDePagamento> query = this.manager.createQuery(
				"select x from TipoDePagamento x", TipoDePagamento.class);

		return query.getResultList();
	}

	@Override
	public List<Pagamento> listar(Date dataInicio, Date dataFim) {
		List<Pagamento> lista = new ArrayList<Pagamento>();
		String lcQuery = "select x from Pagamento x where 1=1";
		if (dataInicio != null ) {
			lcQuery += " where date(x.dataCompra)>=date(:dataInicio)";
		}
		if (dataFim != null) {
			lcQuery += " and date(x.dataCompra)<=date(:dataFim)";
		}
		lcQuery += " order by x.dataCompra desc";
		TypedQuery<Pagamento> query = this.manager.createQuery(lcQuery, Pagamento.class);
		
		if (dataInicio != null ) {
			query.setParameter("dataInicio", dataInicio);
		}
		if (dataFim != null) {
			query.setParameter("dataFim", dataFim);
		}

		lista = query.getResultList();
		return lista;
	}

	@Override
	public ParcelaPagamento adicionaParcela(ParcelaPagamento parcela) {
		return manager.merge(parcela);
	}
	
}
