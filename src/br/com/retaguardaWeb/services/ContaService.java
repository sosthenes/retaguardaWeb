package br.com.retaguardaWeb.services;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.DescricaoPagamento;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.Periodicidade;
import br.com.retaguardaWeb.entidades.TipoDePagamento;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.vo.GastoVO;

@Stateless
public class ContaService {

	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private PeriodicidadeService periodicidade;
	
	
	DecimalFormat qtdeParser = new DecimalFormat( "0.00");
	

	public void adiciona(Pagamento pagamento) {
		this.manager.merge(pagamento);
	}
	
	public void remover(Pagamento pagamento) {
		this.manager.remove(pagamento);
	}

	public void alterar(Pagamento pagamento) {
		this.manager.merge(pagamento);
	}

	
	
	public List<TipoDePagamento> getTipoDePagamentos() {
		TypedQuery<TipoDePagamento> query = this.manager.createQuery(
				"select x from TipoDePagamento x", TipoDePagamento.class);

		return query.getResultList();
	}

	public TipoDePagamento TipoDePagamentoPorId(TipoDePagamento ingred) {
		return this.manager.find(TipoDePagamento.class, ingred);
	}

	public List<Pagamento> listar(Date dataInicio, Date dataFim, Loja loja) {
		List<Pagamento> lista = new ArrayList<Pagamento>();
		String lcQuery = "select distinct x from Pagamento x where 1=1  and x.idLoja=:loja";
		if(dataInicio!=null){
			lcQuery+= " and date(x.dataCompra)>=date(:dataInicio)";
		}
		if(dataFim!=null){
			lcQuery+= " and date(x.dataCompra)<=date(:dataFim)";
		}
			
		lcQuery+=  " order by x.dataCompra desc";
		TypedQuery<Pagamento> query = this.manager.createQuery(lcQuery, Pagamento.class);
		query.setParameter("loja", loja);
		if(dataInicio!=null){
			query.setParameter("dataInicio", dataInicio);
		}
		if(dataFim!=null){
			query.setParameter("dataFim", dataFim);
		}
		lista = query.getResultList();
		return lista;
	}
	
	
	public void cadastroDeContaAvista(TipoDePagamento tipoPagamento, double valor, DescricaoPagamento descricaoPagamento, Date dataPagamento) {
		Pagamento conta = new Pagamento();
		Periodicidade periodicidade = new Periodicidade();
		conta.setTipoPagamento(tipoPagamento);
		conta.setValor(valor);
		conta.setPago(true);
		conta.setValorPago(valor);
		conta.setQuantidade(1);
		conta.setDiaPgto(dataPagamento.getDay());
		periodicidade.setId(1L);
		conta.setPeriodocidade(periodicidade);
		conta.setDataCompra(dataPagamento);
		conta.setDataFim(dataPagamento);
		conta.setDataPagamento(dataPagamento);
		conta.setDescricaoPagamento(descricaoPagamento);
		conta.setParcelas(populaParcelaAvista(conta));
		adicionaParcela(conta, true);

	}
	
	
	private List<ParcelaPagamento> populaParcelaAvista(Pagamento conta) {
		List<ParcelaPagamento> parcelas = new ArrayList<ParcelaPagamento>();
		ParcelaPagamento parcela = new ParcelaPagamento();
		parcela.setPagamento(conta);
		parcela.setDataPagamento(conta.getDataPagamento());
		parcela.setDataParcela(conta.getDataPagamento());
		parcela.setDescricao(conta.getDescricao());
		parcela.setNumParcela(1);
		parcela.setPago(true);
		parcela.setValorPago(conta.getValor());
		parcela.setValorParcela(conta.getValor());
		parcelas.add(parcela);
		return parcelas;

	}
	
	
	public boolean adicionaParcela(Pagamento conta, boolean pago) {
		conta.setPeriodocidade(periodicidade.PeriodicidadePorId(conta.getPeriodocidade()));
		List<ParcelaPagamento> parcelas = new ArrayList<ParcelaPagamento>();
		for(int i =1;conta.getQuantidade()!=null&&i<=conta.getQuantidade();i++){
			ParcelaPagamento parcela = new ParcelaPagamento();
			parcela.setPagamento(conta);
			if(i==1){
				parcela.setDataParcela(conta.getDataCompra());
			}else{
				if(conta.getPeriodocidade().getNumeroDeDias()>=30 && conta.getPeriodocidade().getNumeroDeDias()<300){
					parcela.setDataParcela(new Conversoes().incrementaMesDataDate(conta.getDataCompra(), (conta.getPeriodocidade().getNumeroDeDias()*i)));
				}else if(conta.getPeriodocidade().getNumeroDeDias()>300){
					parcela.setDataParcela(new Conversoes().incrementaAnoADataDate(conta.getDataCompra(), (conta.getPeriodocidade().getNumeroDeDias()*i)));
				}else{
					parcela.setDataParcela(new Conversoes().incrementaDiasADataDate(conta.getDataCompra(), (conta.getPeriodocidade().getNumeroDeDias()*i)));
				}
				
			}
			parcela.setNumParcela(i);
			parcela.setValorParcela(conta.getValor());
			conta.setDescricaoPagamento(buscarDescricaoPorId(conta.getDescricaoPagamento()));
			parcela.setDescricao(conta.getDescricao()==null?conta.getDescricaoPagamento().getDescricaogasto():conta.getDescricao());
			parcela.setPago(pago);
			parcelas.add(parcela);
			//parcela.getPagamento().setParcelas(null);
			//parcela = manager.merge(parcela);
			//conta = parcela.getPagamento();
		}		
		conta.setParcelas(parcelas);
		
		conta = manager.merge(conta);
		
		return true;
	}

	public List<ParcelaPagamento> contasDoDiaAPagar() {
		List<ParcelaPagamento> lista = new ArrayList<ParcelaPagamento>();
		String lcQuery = "select distinct x from ParcelaPagamento x where  1=1";
			//lcQuery+= " and date(x.dataParcela)>=date(:dataInicio)";
			//lcQuery+= " and date(x.dataParcela)<=date(:dataFim)";
		TypedQuery<ParcelaPagamento> query = this.manager.createQuery(lcQuery, ParcelaPagamento.class);
			//query.setParameter("dataInicio", new Date());
			//query.setParameter("dataFim", new Date());
		lista = query.getResultList();
		return lista;
	}

	public void removerParcelas(ParcelaPagamento parcela) {
		Pagamento pagto = new Pagamento();
		pagto.setId(parcela.getPagamento().getId());
		
		
		String sql = "delete FROM parcelas where idPagamento = ?";
		String sql2 = "delete FROM pagamento where idPagamento = ?";

		Query query_ = manager.createNativeQuery(sql, ParcelaPagamento.class);
		query_.setParameter(1, parcela.getPagamento().getId());
		query_.executeUpdate();
		
		Query query = manager.createNativeQuery(sql2, Pagamento.class);
		query.setParameter(1, parcela.getPagamento().getId());
		query.executeUpdate();
		

		
	}
	
	public DescricaoPagamento buscarDescricaoPorId(DescricaoPagamento descricao) {
		DescricaoPagamento resultado = null;
		TypedQuery<DescricaoPagamento> query = this.manager.createQuery("select x from DescricaoPagamento x "
				+ " where x.id = "+descricao.getId(), DescricaoPagamento.class);
		try {
			resultado = (DescricaoPagamento) query.getSingleResult();
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<GastoVO> totalGastoPorTipoConta(Date dataInicio, Date dataFim) {

			List<GastoVO> lista = new ArrayList<GastoVO>();
			String lcQuery = "SELECT t.descricao, sum(if(p.valorPago is null, p.valorparcela, p.valorPago)) valor, pgt.idTipopagamento"
					+ " FROM parcelas p "
					+ " inner  join pagamento pgt on p.`idPagamento` = pgt.`idPagamento`"
					+ " inner  join tipodepagamento t on pgt.idTipoPagamento = t.idTipoPagamento";
			lcQuery+= " where p.pago = true";
			
			if(dataInicio!=null){
				lcQuery+= " and date(p.dataPagamento)>=date(:dataInicio)";
			}
			if(dataFim!=null){
				lcQuery+= " and date(p.dataPagamento)<=date(:dataFim)";
			}
			lcQuery+=  " group by pgt.idTipopagamento";
			lcQuery+=  " order by valor desc";
				
			Query query = manager.createNativeQuery(lcQuery);
			if(dataInicio!=null){
				query.setParameter("dataInicio", dataInicio);
			}
			if(dataFim!=null){
				query.setParameter("dataFim", dataFim);
			}
			List<Object[]> results = query.getResultList();
			double valorTotal = 0;
			for(Object[] o : results){
				valorTotal+=Double.valueOf(o[1].toString());
			}
			
			for(Object[] o : results){
				GastoVO g = new GastoVO();
				g.setDescricao((String) o[0]);
				g.setValor((String) qtdeParser.format(o[1]).toString());
				g.setPorcentagem(qtdeParser.format(((Double.valueOf(o[1].toString())*100)/valorTotal)));
				g.setTotal(qtdeParser.format(valorTotal));
				g.setListaGasto(listaGasto(o[2].toString(), dataInicio, dataFim));
				lista.add(g);
			}
		return lista;
	}

	private List<GastoVO> listaGasto(String id, Date dataInicio, Date dataFim) {
		List<GastoVO> lista = new ArrayList<GastoVO>();
		String lcQuery = "SELECT d.descricao, sum(if(p.valorPago is null, p.valorParcela, p.valorPago)) valor"
				+ " FROM parcelas p "
				+ " inner join pagamento pg on p.idPagamento= pg.idPagamento"
				+ " inner join descricaogasto d on pg.iddescricaogasto = d.iddescricaogasto";
		lcQuery+= " where p.pago = true"
				+ " and pg.idTipopagamento = "+id;
		
		if(dataInicio!=null){
			lcQuery+= " and date(p.dataPagamento)>=date(:dataInicio)";
		}
		if(dataFim!=null){
			lcQuery+= " and date(p.dataPagamento)<=date(:dataFim)";
		}
		lcQuery+=  " group by d.descricao";
		lcQuery+=  " order by valor, pg.idTipopagamento, d.descricao";
			
		Query query = manager.createNativeQuery(lcQuery);
		if(dataInicio!=null){
			query.setParameter("dataInicio", dataInicio);
		}
		if(dataFim!=null){
			query.setParameter("dataFim", dataFim);
		}
		List<Object[]> results = query.getResultList();
		double valorTotal = 0;
		for(Object[] o : results){
			valorTotal+=Double.valueOf(o[1].toString());
		}
		
		for(Object[] o : results){
			GastoVO g = new GastoVO();
			g.setDescricao((String) o[0]);
			g.setValor((String) qtdeParser.format(o[1]).toString());
			g.setPorcentagem(qtdeParser.format(((Double.valueOf(o[1].toString())*100)/valorTotal)));
			g.setTotal(qtdeParser.format(valorTotal));
			lista.add(g);
		}
	return lista;
	}
}
