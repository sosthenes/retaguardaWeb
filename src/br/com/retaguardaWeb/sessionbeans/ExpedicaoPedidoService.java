package br.com.retaguardaWeb.sessionbeans;

import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.ExpedicaoPedido;
import br.com.retaguardaWeb.entidades.Pedido;

@Stateless
public class ExpedicaoPedidoService {

	

	@PersistenceContext
	private EntityManager manager;

	public void mantemExpedicao(ExpedicaoPedido expedicao) {
		try {
			manager.merge(expedicao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ExpedicaoPedido recuperaExpedicaoPorPedido(Pedido p) {
		String sql = "select x from ExpedicaoPedido x"
				+ " where x.idPedido = :idPedido";
		TypedQuery<ExpedicaoPedido> query = this.manager.createQuery(sql, ExpedicaoPedido.class);
		query.setParameter("idPedido", p);
		try {
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public ExpedicaoPedido recebePedido(ExpedicaoPedido exped) {
		exped.setDatahoraChegada(new Date());
		return manager.merge(exped);
	}
	
	
}
