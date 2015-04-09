package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.entidades.DescricaoPagamento;
import br.com.retaguardaWeb.entidades.Fechamentocaixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.entidades.TipoDePagamento;
import br.com.retaguardaWeb.util.ConstantesSistema;
import br.com.retaguardaWeb.vo.GastoCaixaVO;

@Stateless
public class GastoService {

	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private ContaService contaService;

	public void adiciona(GastoCaixa gastoCaixa) {
		this.manager.merge(gastoCaixa);
		contaAvistaVale(gastoCaixa);
	}
	private void contaAvistaVale(GastoCaixa dados) {
		TipoDePagamento tipoPagamento = new TipoDePagamento();
		tipoPagamento.setId(ConstantesSistema.TIPO_PAGAMENTO_GASTO_CAIXA);
		double valor = dados.getValor();
		DescricaoPagamento descricaoPagamento  = new DescricaoPagamento();
		descricaoPagamento.setId(ConstantesSistema.DESCRICAO_PAGAMENTO_GASTO_CAIXA);
		Date dataPagamento = new Date();
		contaService.cadastroDeContaAvista(tipoPagamento, valor, descricaoPagamento, dataPagamento);

	}
	
	public void remover(GastoCaixa gastoCaixa) {
		gastoCaixa = manager.merge(gastoCaixa);
		this.manager.remove(gastoCaixa);
	}

	public void alterar(GastoCaixa gastoCaixa) {
		this.manager.merge(gastoCaixa);
	}

	
	
	public List<GastoCaixa> getGastoCaixas() {
		TypedQuery<GastoCaixa> query = this.manager.createQuery(
				"select x from GastoCaixa x", GastoCaixa.class);

		return query.getResultList();
	}

	public GastoCaixa gastoCaixaPorId(GastoCaixa ingred) {
		return this.manager.find(GastoCaixa.class, ingred);
	}

	public List<GastoCaixa> listaGastosPorCaixa(Caixa caixa) {
		List<GastoCaixa> lista = new ArrayList<GastoCaixa>();
		String lcQuery = "select x from GastoCaixa x ";
		/*if(caixa!=null && caixa.getId()!=null){
			lcQuery+= " where x.caixa=:idCaixa";
		}*/
		TypedQuery<GastoCaixa> query = this.manager.createQuery(lcQuery, GastoCaixa.class);
		/*if(caixa!=null && caixa.getId()!=null){
			query.setParameter("idCaixa", caixa);
		}*/
		lista = query.getResultList();
		return lista;
	}

	public List<GastoCaixa> relatorioListaGasto(GastoCaixaVO gastoVO) {
		List<GastoCaixa> lista = new ArrayList<GastoCaixa>();
		String lcQuery = "select x from GastoCaixa x where DATE_FORMAT(x.dataHora, '%d/%m/%Y')=:data";
		TypedQuery<GastoCaixa> query = this.manager.createQuery(lcQuery, GastoCaixa.class);
		query.setParameter("data", gastoVO.getDatagastoCaixa());
		lista = query.getResultList();
		return lista;
	}

	public List<GastoCaixa> relatorioListaGastoPorPeriodo(Date dataInicio, Date dataFim) {
			List<GastoCaixa> lista = new ArrayList<GastoCaixa>();
			String lcQuery = "select x from GastoCaixa x ";
			lcQuery+= " where date(x.dataHora)>=date(:dataInicio)"
					+ " and date(x.dataHora)<=date(:dataFim)";
			TypedQuery<GastoCaixa> query = this.manager.createQuery(lcQuery, GastoCaixa.class);
			query.setParameter("dataInicio", dataInicio);
			query.setParameter("dataFim", dataFim);
			lista = query.getResultList();
			return lista;
	}

	public double getGastoPorCaixaConsolidado(Date data, Caixa caixa) {
		TypedQuery<Double> query = this.manager.createQuery("select SUM(x.valor) from GastoCaixa x where x.caixa = :caixa"
				+ " and date(x.dataHora) = date(:data)", Double.class);
		query.setParameter("caixa", caixa);
		query.setParameter("data", data);
		try {
			double resultado = 0;
			if(query.getSingleResult()!=null)
				resultado = (Double) query.getSingleResult();
			
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<GastoCaixa> listaGastosPorCaixa(Caixa caixa, Date dataInicio) {
		List<GastoCaixa> lista = new ArrayList<GastoCaixa>();
		String lcQuery = "select x from GastoCaixa x ";
		lcQuery+= " where date(x.dataHora)=date(:dataInicio)"
				+ " and x.caixa=:caixa";
		TypedQuery<GastoCaixa> query = this.manager.createQuery(lcQuery, GastoCaixa.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("caixa", caixa);
		lista = query.getResultList();
		return lista;
	}
	

}
