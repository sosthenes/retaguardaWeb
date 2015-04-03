package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.entidades.Pedido;

@Stateless
public class PedidoService {

	
	@PersistenceContext
	private EntityManager manager;



	
	public List<Pedido> getPedidos(CaixaPeriodoFuncionario caixaPeriodoFuncionario, FormaPagamento f) {
		String sql = "select x from Pedido x"
				+ " where x.caixaPeriodoFuncionario = :caixaPeriodoFuncionario";
		if(f!=null && f.getId()!=null)
			sql+=  " and x.formaPagamento = :formaPagamento"
			
				+ " order by id desc";
		List<Pedido> listapedidos = new ArrayList<Pedido>();
		TypedQuery<Pedido> query = this.manager.createQuery(sql, Pedido.class);
		query.setParameter("caixaPeriodoFuncionario", caixaPeriodoFuncionario);
		if(f!=null && f.getId()!=null)
			query.setParameter("formaPagamento", f);
		
		try {
			listapedidos = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listapedidos;
	}




	public Double getTotalPedidos(CaixaPeriodoFuncionario valoresCaixaAtual, FormaPagamento f) {
		String sql = "select Sum(x.totalPedido) from Pedido x"
				+ " where x.caixaPeriodoFuncionario = :caixaPeriodoFuncionario";
		if(f!=null && f.getId()!=null)
			sql+=  " and x.formaPagamento = :formaPagamento"
			
				+ " order by id desc";
		Pedido listapedidos = new Pedido();
		TypedQuery<Double> query = this.manager.createQuery(sql, Double.class);
		query.setParameter("caixaPeriodoFuncionario", valoresCaixaAtual);
		if(f!=null && f.getId()!=null)
			query.setParameter("formaPagamento", f);
		
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

}
