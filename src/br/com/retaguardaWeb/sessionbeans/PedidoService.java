package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.entidades.Pedido;
import br.com.retaguardaWeb.entidades.TipoVenda;

@Stateless
public class PedidoService {

	
	@PersistenceContext
	private EntityManager manager;


	@EJB
	ExpedicaoPedidoService expedicaoService;
	
	public List<Pedido> getPedidos(CaixaPeriodoFuncionario caixaPeriodoFuncionario, FormaPagamento f, TipoVenda tipoVenda) {
		String sql = "select x from Pedido x"
				+ " where x.caixaPeriodoFuncionario = :caixaPeriodoFuncionario";
		if(f!=null && f.getId()!=null){
			sql+=  " and x.formaPagamento = :formaPagamento";
		}
		if(tipoVenda!=null && tipoVenda.getId()!=null){
			sql+=  " and x.tipoPedido = :tipoPedido";
		}
			
		sql+=  " order by id desc";
		List<Pedido> listapedidos = new ArrayList<Pedido>();
		TypedQuery<Pedido> query = this.manager.createQuery(sql, Pedido.class);
		query.setParameter("caixaPeriodoFuncionario", caixaPeriodoFuncionario);
		if(f!=null && f.getId()!=null)
			query.setParameter("formaPagamento", f);
		if(tipoVenda!=null && tipoVenda.getId()!=null){
			query.setParameter("tipoPedido", tipoVenda);
		}
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




	public List<Pedido> getPedidosExpedicao(CaixaPeriodoFuncionario caixaPeriodoFuncionario) {
		String sql = "select x from Pedido x"
				+ " where x.caixaPeriodoFuncionario = :caixaPeriodoFuncionario"
				+ " and x.expedicao=true";
		sql+=  " order by id desc";
		List<Pedido> listapedidos = new ArrayList<Pedido>();
		TypedQuery<Pedido> query = this.manager.createQuery(sql, Pedido.class);
		query.setParameter("caixaPeriodoFuncionario", caixaPeriodoFuncionario);
		List<Pedido> listaFinal =null;
		try {
			listapedidos = query.getResultList();
			if(listapedidos!=null && listapedidos.size()>0){
				listaFinal = new ArrayList<Pedido>();
				for(Pedido p : listapedidos){
					Pedido pedido = new Pedido();
					pedido = p;
					pedido.setExpedicaoPedido(expedicaoService.recuperaExpedicaoPorPedido(p));
					listaFinal.add(pedido);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaFinal;
	}

}
