package br.com.retaguardaWeb.services;

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
import br.com.retaguardaWeb.entidades.Diariafuncionario;
import br.com.retaguardaWeb.entidades.TipoDePagamento;
import br.com.retaguardaWeb.entidades.Valefuncionario;
import br.com.retaguardaWeb.util.ConstantesSistema;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.util.Extenso;
import br.com.retaguardaWeb.vo.DiariafuncionarioVO;

@Stateless
@TransactionManagement
public class DiariaFuncionarioService {

	Format formatMes = new SimpleDateFormat("MM");  
	Format formatAno = new SimpleDateFormat("yyyy");  

	@EJB
	private DiariaFuncionarioService diariafuncionarioService;
	@EJB
	private RelatorioService relatorioService;
	
	@EJB
	private ContaService contaService;
	
	@PersistenceContext
	private EntityManager manager;

	public Diariafuncionario adiciona(Diariafuncionario diariafuncionario) {
		Diariafuncionario adver= new Diariafuncionario();
		adver= this.manager.merge(diariafuncionario);
		contaAvistaVale(diariafuncionario);
		return adver;
	}
	
	private void contaAvistaVale(Diariafuncionario dados) {
		TipoDePagamento tipoPagamento = new TipoDePagamento();
		tipoPagamento.setId(ConstantesSistema.TIPO_PAGAMENTO_DIARIAS);
		double valor = Double.parseDouble(dados.getValor().replaceAll(",", "."));
		DescricaoPagamento descricaoPagamento  = new DescricaoPagamento();
		descricaoPagamento.setId(ConstantesSistema.DESCRICAO_PAGAMENTO_DIARIAS);
		Date dataPagamento = new Date();
		contaService.cadastroDeContaAvista(tipoPagamento, valor, descricaoPagamento, dataPagamento);

	}
	
	public void remover(Diariafuncionario diariafuncionario) {
		diariafuncionario = manager.merge(diariafuncionario);
		this.manager.remove(diariafuncionario);
	}

	public void alterar(Diariafuncionario diariafuncionario) {
		this.manager.merge(diariafuncionario);
	}

	
	
	public List<Diariafuncionario> getDiariafuncionarios() {
		TypedQuery<Diariafuncionario> query = this.manager.createQuery(
				"select x from Diariafuncionario x", Diariafuncionario.class);

		return query.getResultList();
	}

	public Diariafuncionario diariafuncionarioPorId(Diariafuncionario idDiariafuncionario) {
		return this.manager.find(Diariafuncionario.class, idDiariafuncionario);
	}

	public void salvaDiariafuncionario(Diariafuncionario adv) {
		this.manager.merge(adv);
	}

	public List<Diariafuncionario> listas(Diariafuncionario adv) {
		List<Diariafuncionario> s = new ArrayList<Diariafuncionario>();
		TypedQuery<Diariafuncionario> query = this.manager.createQuery("select x from Diariafuncionario x where x.diariafuncionario = :diariafuncionario", Diariafuncionario.class);
		query.setParameter("diariafuncionario", adv);
		try {
			s = (List<Diariafuncionario>) query.getResultList();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void gerarAdv(Diariafuncionario adv) {
		String jasper = "diariafuncionario.jasper";
		String titulo = "RECIBO DE PAGAMENTO DE SERVI�OS";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("adv", "");
		try {


			
			String dataDebito = new Conversoes().recuperaMesAnoExtenso(new Conversoes().incrementaMesADataDate(adv.getDataDiaria()));
					
	        Extenso teste = new Extenso(new BigDecimal(adv.getValor()));
			
			List<DiariafuncionarioVO> advVO = new ArrayList<DiariafuncionarioVO>();
			DiariafuncionarioVO diariafuncionarioVO = new DiariafuncionarioVO();
			diariafuncionarioVO.setDiariaFuncionario(adv);
			diariafuncionarioVO.setValorPorExtenso(teste.toString());
			diariafuncionarioVO.setDataDebito(dataDebito);
			advVO.add(diariafuncionarioVO);
			relatorioService.pdf(jasper, titulo, "", "", advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String pegaValorUtilizadoMes(Long idFuncionario, Date data) {
		String resultado = null;
		TypedQuery query = this.manager.createQuery("select SUM(x.valor) from Diariafuncionario x "
				+ " where x.funcionario.id = :funcionario"
				+ " and MONTH(x.dataDiaria) = :mes"
				+ " and Year(x.dataDiaria) = :ano", String.class);
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

	public List<Diariafuncionario> getDiariafuncionariosPorFuncionario(
			Long idFuncionario, Date dataConsulta) {
		List<Diariafuncionario> s = new ArrayList<Diariafuncionario>();
		TypedQuery<Diariafuncionario> query = this.manager.createQuery("select x from Diariafuncionario x where x.funcionario.id = :funcionario"
				+ " and MONTH(x.dataDiaria) = :mes"
				+ " and Year(x.dataDiaria) = :ano", Diariafuncionario.class);
		query.setParameter("funcionario", idFuncionario);
		query.setParameter("mes", Integer.parseInt(formatMes.format(dataConsulta.getTime())));
		query.setParameter("ano", Integer.parseInt(formatAno.format(dataConsulta.getTime())));
		try {
			s = (List<Diariafuncionario>) query.getResultList();
			return s;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Diariafuncionario> listasDiariaPorCaixa(Caixa caixa, Date dataInicio) {
		List<Diariafuncionario> lista = new ArrayList<Diariafuncionario>();
		String lcQuery = "select x from Diariafuncionario x ";
		lcQuery+= " where date(x.dataDiaria)>=date(:dataInicio)"
				+ " and x.caixa=:caixa";
		TypedQuery<Diariafuncionario> query = this.manager.createQuery(lcQuery, Diariafuncionario.class);
		query.setParameter("dataInicio", dataInicio);
		query.setParameter("caixa", caixa);
		lista = query.getResultList();
		return lista;
	}
	


	
}
