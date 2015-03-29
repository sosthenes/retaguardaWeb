package br.com.retaguardaWeb.sessionbeans;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRException;
import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.DescricaoPagamento;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.Periodicidade;
import br.com.retaguardaWeb.entidades.TipoDePagamento;
import br.com.retaguardaWeb.entidades.Valefuncionario;
import br.com.retaguardaWeb.util.ConstantesSistema;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.util.Extenso;
import br.com.retaguardaWeb.vo.ValefuncionarioVO;

@Stateless
@TransactionManagement
public class ValeFuncionarioService {

	Format formatMes = new SimpleDateFormat("MM");  
	Format formatAno = new SimpleDateFormat("yyyy");  

	@EJB
	private ValeFuncionarioService valefuncionarioService;
	@EJB
	private RelatorioService relatorioService;
	
	@EJB
	private ContaService contaService;
	
	
	
	@PersistenceContext
	private EntityManager manager;

	public Valefuncionario adiciona(Valefuncionario valefuncionario) {
		Valefuncionario adver= new Valefuncionario();
		adver= this.manager.merge(valefuncionario);
		contaAvistaVale(adver, new Date());
		return adver;
	}
	
	
	private void contaAvistaVale(Valefuncionario dados, Date dataPagamento) {
		TipoDePagamento tipoPagamento = new TipoDePagamento();
		tipoPagamento.setId(ConstantesSistema.TIPO_PAGAMENTO_VALE_FUNCIONARIO);
		double valor = Double.parseDouble(dados.getValor().replaceAll(",", "."));
		DescricaoPagamento descricaoPagamento  = new DescricaoPagamento();
		descricaoPagamento.setId(ConstantesSistema.DESCRICAO_PAGAMENTO_VALE_FUNCIONARIO);
		descricaoPagamento = contaService.buscarDescricaoPorId(descricaoPagamento);
		contaService.cadastroDeContaAvista(tipoPagamento, valor, descricaoPagamento, dataPagamento);

	}
	
	
	@SuppressWarnings("unused")
	private Pagamento populaConta(Pagamento conta) {
		TipoDePagamento tipoConta = new TipoDePagamento();
		Periodicidade periodicidade = new Periodicidade();
		DescricaoPagamento descricaoPagamento = new DescricaoPagamento();
		
		tipoConta.setId(5L);
		conta.setTipoPagamento(tipoConta);
		conta.setDataCompra(new  Date());
		conta.setDataFim(new  Date());
		periodicidade = new  Periodicidade();
		periodicidade.setId(1L);
		conta.setPeriodocidade(periodicidade);
		descricaoPagamento.setId(14l);
		conta.setDescricaoPagamento(descricaoPagamento);
		return conta;

	}
	
	public void remover(Valefuncionario valefuncionario) {
		valefuncionario = manager.merge(valefuncionario);
		this.manager.remove(valefuncionario);
	}

	public void alterar(Valefuncionario valefuncionario) {
		this.manager.merge(valefuncionario);
	}

	
	
	public List<Valefuncionario> getValefuncionarios() {
		TypedQuery<Valefuncionario> query = this.manager.createQuery(
				"select x from Valefuncionario x", Valefuncionario.class);

		return query.getResultList();
	}

	public Valefuncionario valefuncionarioPorId(Valefuncionario idValefuncionario) {
		return this.manager.find(Valefuncionario.class, idValefuncionario);
	}

	public void salvaValefuncionario(Valefuncionario adv) {
		this.manager.merge(adv);
	}

	public List<Valefuncionario> listas(Valefuncionario adv) {
		List<Valefuncionario> s = new ArrayList<Valefuncionario>();
		TypedQuery<Valefuncionario> query = this.manager.createQuery("select x from Valefuncionario x where x.valefuncionario = :valefuncionario", Valefuncionario.class);
		query.setParameter("valefuncionario", adv);
		try {
			s = (List<Valefuncionario>) query.getResultList();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarAdv(Valefuncionario adv) {
		String jasper = "valefuncionario.jasper";
		String titulo = "RECIBO DE ADIANTAMENTO SALARIAL";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("adv", "");
		try {


			
			String dataDebito = new Conversoes().recuperaMesAnoExtenso(new Conversoes().incrementaMesADataDate(adv.getDataVale()));
			if(adv.getValor()!=null && !adv.getValor().equals("")){
				adv.setValor(adv.getValor().replaceAll(",", "."));
			}else{
				adv.setValor("0");
			}
	        Extenso teste = new Extenso(new BigDecimal(adv.getValor()));
			
			List<ValefuncionarioVO> advVO = new ArrayList<ValefuncionarioVO>();
			ValefuncionarioVO valefuncionarioVO = new ValefuncionarioVO();
			valefuncionarioVO.setValeFuncionario(adv);
			valefuncionarioVO.setValorPorExtenso(teste.toString());
			valefuncionarioVO.setDataDebito(dataDebito);
			advVO.add(valefuncionarioVO);
			relatorioService.pdf(jasper, titulo, "", "", advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String pegaValorUtilizadoMes(Long idFuncionario, Date data) {
		String resultado = null;
		TypedQuery<String> query = this.manager.createQuery("select SUM(x.valor) from Valefuncionario x "
				+ " where x.funcionario.id = :funcionario"
				+ " and MONTH(x.dataVale) = :mes"
				+ " and Year(x.dataVale) = :ano", String.class);
		query.setParameter("funcionario", idFuncionario);
		query.setParameter("mes", Integer.parseInt(formatMes.format(data.getTime())));
		query.setParameter("ano", Integer.parseInt(formatAno.format(data.getTime())));
		try {
			resultado = (String) query.getSingleResult();
			return resultado==null?"0":resultado;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
	}

	public List<Valefuncionario> getValefuncionariosPorFuncionario(
			Long idFuncionario, Date data) {
		List<Valefuncionario> s = new ArrayList<Valefuncionario>();
		TypedQuery<Valefuncionario> query = this.manager.createQuery("select x from Valefuncionario x where x.funcionario.id = :funcionario"
				+ " and MONTH(x.dataVale) = :mes"
				+ " and Year(x.dataVale) = :ano", Valefuncionario.class);
		query.setParameter("funcionario", idFuncionario);
		query.setParameter("mes", Integer.parseInt(formatMes.format(data.getTime())));
		query.setParameter("ano", Integer.parseInt(formatAno.format(data.getTime())));
		try {
			s = (List<Valefuncionario>) query.getResultList();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Valefuncionario> getValefuncionariosPorFuncionarioData(Long idFuncionario, Date inicio, Date fim) {
		List<Valefuncionario> s = new ArrayList<Valefuncionario>();
		TypedQuery<Valefuncionario> query = this.manager.createQuery("select x from Valefuncionario x where x.funcionario.id = :funcionario"
				+ " and date(x.dataVale) >= date(:inicio)"
				+ " and date(x.dataVale) <= date(:fim)", Valefuncionario.class);
		query.setParameter("funcionario", idFuncionario);
		query.setParameter("inicio", inicio);
		query.setParameter("fim", fim);
		try {
			s = (List<Valefuncionario>) query.getResultList();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Double getValefuncionariosPorCaixaConsolidado(Date data, Caixa caixa) {
		TypedQuery<String> query = this.manager.createQuery("select SUM(x.valor) from Valefuncionario x where x.caixa = :caixa"
				+ " and date(x.dataVale) = date(:data)", String.class);
		query.setParameter("caixa", caixa);
		query.setParameter("data", data);
		try {
			String total = (String) query.getSingleResult();
			if(total==null || total.equals("")){
				total = "0";
			}
			return Double.parseDouble(total.replaceAll(",", "."));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0.0;
	}

	public List<Valefuncionario> getValefuncionariosPorPeriodo(Date dataInicio,
			Date dataFim) {
		List<Valefuncionario> lista = new ArrayList<Valefuncionario>();
		String lcQuery = "select x from Valefuncionario x ";
		lcQuery+= " where date(x.dataVale)>=date(:dataInicio)"
				+ " and date(x.dataVale)<=date(:dataFim)";
		TypedQuery<Valefuncionario> query = this.manager.createQuery(lcQuery, Valefuncionario.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		lista = query.getResultList();
		return lista;
	}

	public List<Valefuncionario> listasValePorCaixa(Caixa caixa, Date dataInicio) {
		List<Valefuncionario> lista = new ArrayList<Valefuncionario>();
		String lcQuery = "select x from Valefuncionario x ";
		lcQuery+= " where date(x.dataVale)>=date(:dataInicio)"
				+ " and x.caixa=:caixa";
		TypedQuery<Valefuncionario> query = this.manager.createQuery(lcQuery, Valefuncionario.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("caixa", caixa);
		lista = query.getResultList();
		return lista;

	}

	public List<ValefuncionarioVO> getTodosValefuncionariosPorFuncionarioData(Date dataInicio, Date dataFim) {
		List<ValefuncionarioVO> lista = new ArrayList<ValefuncionarioVO>();
		String lcQuery = "select new br.com.retaguardaWeb.vo.ValefuncionarioVO(x.funcionario, Sum(x.valor)) from Valefuncionario x ";
		lcQuery+= " where date(x.dataVale)>=date(:dataInicio)"
				+ " and date(x.dataVale)<=date(:dataFim)"
				+ " group by x.funcionario";
		TypedQuery<ValefuncionarioVO> query = this.manager.createQuery(lcQuery, ValefuncionarioVO.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("dataFim", dataFim);
		lista = query.getResultList();
		return lista;

	}
	


	
}
