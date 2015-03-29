package br.com.retaguardaWeb.sessionbeans;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRException;
import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.QuilometroMotoBoy;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.util.Extenso;
import br.com.retaguardaWeb.vo.QuilometragemGasolinaVO;
import br.com.retaguardaWeb.vo.ValefuncionarioVO;

@Stateless
public class QuilometragemBoyService {

	
	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private RelatorioService relatorioService;

	public void adiciona(QuilometroMotoBoy quilometroMotoBoy) {
		this.manager.merge(quilometroMotoBoy);
	}

	public void remover(QuilometroMotoBoy quilometroMotoBoy) {
		quilometroMotoBoy = this.manager.merge(quilometroMotoBoy);
		this.manager.remove(quilometroMotoBoy);
	}

	
	public List<QuilometroMotoBoy> getQuilometroMotoBoys() {
		List<QuilometroMotoBoy> listaquilometroMotoBoys = new ArrayList<QuilometroMotoBoy>();
		TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery("select x from QuilometroMotoBoy x", QuilometroMotoBoy.class);
		try {
			listaquilometroMotoBoys = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaquilometroMotoBoys;
	}


	public QuilometroMotoBoy pesquisaPorId(QuilometroMotoBoy quilometroMotoBoy) {
		TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery("select x from QuilometroMotoBoy x where idQuilometroMotoBoy = :id", QuilometroMotoBoy.class);
		query.setParameter("id", quilometroMotoBoy.getId());
		return query.getSingleResult();
	}

	public List<QuilometroMotoBoy>  pesquisaKmsPorId(QuilometroMotoBoy quilometroMotoBoy) {
		List<QuilometroMotoBoy> listaquilometroMotoBoys = new ArrayList<QuilometroMotoBoy>();
		String lcQuery = "select x from QuilometroMotoBoy x";
		if(quilometroMotoBoy.getFuncionario()!=null && quilometroMotoBoy.getFuncionario().getId()>0){
			lcQuery += " where x.funcionario = :id";
		}
		lcQuery += " order by x.data desc limit 20";
		
		TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery(lcQuery, QuilometroMotoBoy.class);
		
		if(quilometroMotoBoy.getFuncionario()!=null && quilometroMotoBoy.getFuncionario().getId()>0){
			query.setParameter("id", quilometroMotoBoy.getFuncionario());
		}
		try {
			listaquilometroMotoBoys = query.setMaxResults(60).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaquilometroMotoBoys;
	}

	public List<QuilometroMotoBoy> listaQuilometroMotoBoyPorLoja(Loja loja) {
		List<QuilometroMotoBoy> listaquilometroMotoBoys = new ArrayList<QuilometroMotoBoy>();
		TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery("select x from QuilometroMotoBoy x where x.idLoja=:loja", QuilometroMotoBoy.class);
		try {
			query.setParameter("loja", loja);
			listaquilometroMotoBoys = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listaquilometroMotoBoys;
	}

	public void gerarRecibo(QuilometroMotoBoy kmBoy) {
		String jasper = "reciboGasolina.jasper";
		String titulo = "RECIBO DE PAGAMENTO DE COMBUSTÍVEL";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("adv", "");
		try {
			
	        Extenso valorPago = new Extenso(new BigDecimal(kmBoy.getValorPago().replaceAll(",", ".")));
			
			List<QuilometragemGasolinaVO> relatorio = new ArrayList<QuilometragemGasolinaVO>();
			QuilometragemGasolinaVO km = new QuilometragemGasolinaVO();
			km.setKmInicial(kmBoy.getKmInicial());
			km.setKmFinal(kmBoy.getKmFinal());
			km.setNomeFuncionario(kmBoy.getFuncionario().getNome());
			km.setDataPagamento(kmBoy.getMyFormattedDate());
			km.setValorGasolina(kmBoy.getValorGasolina());
			km.setValorPago(kmBoy.getValorPago());
			km.setValorPagoExtenso(valorPago.toString());
			km.setCpf(kmBoy.getFuncionario().getCpf());
			km.setDepartamento(kmBoy.getFuncionario().getSetor().getDescricao());
			km.setCargo(kmBoy.getFuncionario().getCargo().getDescricao());
			km.setKmTotal(totalRodado(km.getKmInicial(), km.getKmFinal()));;
			km.setMoto(kmBoy.getFuncionario().getMoto()); ;
			km.setPlaca(kmBoy.getFuncionario().getPlaca());;
			km.setRenavam(kmBoy.getFuncionario().getRenavam());
			relatorio.add(km);
			relatorioService.pdf(jasper, titulo, "", "", relatorio, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public String totalRodado(String inicial, String _final){
		
		String retorno = "0";
		double ini = 0;
		double finaL = 0;
		double resultado = 0;
		
		ini = Double.parseDouble(inicial.replaceAll(",", "."));
		finaL = Double.parseDouble(_final.replaceAll(",", "."));
		resultado = finaL -ini;
		
		retorno = String.valueOf(resultado);
		return retorno;
	}

	public List<QuilometroMotoBoy> listaQuilometroMotoPorPeriodo(
			Date dataInicio, Date dataFim) {
		List<QuilometroMotoBoy> lista = new ArrayList<QuilometroMotoBoy>();
		String lcQuery = "select x from QuilometroMotoBoy x ";
		lcQuery+= " where date(x.data)>=date(:dataInicio)"
				+ " and date(x.data)<=date(:dataFim)";
		TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery(lcQuery, QuilometroMotoBoy.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		lista = query.getResultList();
		return lista;
	}

	public double getGasolinasPorCaixaConsolidado(Date data, Caixa caixa) {
		try {
		
			List<QuilometroMotoBoy> listaquilometroMotoBoys = new ArrayList<QuilometroMotoBoy>();
			TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery("select x from QuilometroMotoBoy x where x.caixa = :caixa"
					+ " and date(x.data) = date(:data)", QuilometroMotoBoy.class);
			query.setParameter("caixa", caixa);
			query.setParameter("data", data);
			listaquilometroMotoBoys = query.getResultList();

			double total = 0;
			for(QuilometroMotoBoy q : listaquilometroMotoBoys){
				total += Double.parseDouble(q.getValorPago().replaceAll(",", "."));
			}
			
			return total;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<QuilometroMotoBoy> listaQuilometroMotoBoyPorCaixa(Caixa caixa, Date dataInicio) {
		List<QuilometroMotoBoy> listaquilometroMotoBoys = new ArrayList<QuilometroMotoBoy>();
			try {
				TypedQuery<QuilometroMotoBoy> query = this.manager.createQuery("select x from QuilometroMotoBoy x where x.caixa = :caixa"
						+ " and date(x.data) = date(:data)", QuilometroMotoBoy.class);
				query.setParameter("caixa", caixa);
				query.setParameter("data", dataInicio);
				listaquilometroMotoBoys = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return listaquilometroMotoBoys;
	}
	
}
