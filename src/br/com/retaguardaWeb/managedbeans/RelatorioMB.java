package br.com.retaguardaWeb.managedbeans;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRException;
import br.com.retaguardaWeb.entidades.Advertencia;
import br.com.retaguardaWeb.entidades.AdvertenciaMotivo;
import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Diariafuncionario;
import br.com.retaguardaWeb.entidades.EstadoEstoque;
import br.com.retaguardaWeb.entidades.EstoqueAtual;
import br.com.retaguardaWeb.entidades.Fechamentocaixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.QuilometroMotoBoy;
import br.com.retaguardaWeb.entidades.Valefuncionario;
import br.com.retaguardaWeb.sessionbeans.AdvertenciaService;
import br.com.retaguardaWeb.sessionbeans.ContaService;
import br.com.retaguardaWeb.sessionbeans.DiariaFuncionarioService;
import br.com.retaguardaWeb.sessionbeans.EstoqueAtualServices;
import br.com.retaguardaWeb.sessionbeans.FechamentoCaixaService;
import br.com.retaguardaWeb.sessionbeans.FuncionarioService;
import br.com.retaguardaWeb.sessionbeans.GastoService;
import br.com.retaguardaWeb.sessionbeans.QuilometragemBoyService;
import br.com.retaguardaWeb.sessionbeans.RelatorioService;
import br.com.retaguardaWeb.sessionbeans.ValeFuncionarioService;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.vo.AdvertenciaVO;
import br.com.retaguardaWeb.vo.CaixaConsolidadoVO;
import br.com.retaguardaWeb.vo.FechamentoPorCaixaVO;
import br.com.retaguardaWeb.vo.GastoCaixaVO;
import br.com.retaguardaWeb.vo.GastoVO;
import br.com.retaguardaWeb.vo.RelGastoVO;
import br.com.retaguardaWeb.vo.ValefuncionarioVO;
import br.com.retaguardaWeb.vo.ValesFuncionariosVO;

@ManagedBean
public class RelatorioMB {

	@EJB
	private FuncionarioService funcionarioService;

	@EJB
	private GastoService gastoService;
	
	@EJB
	private ContaService contaService;
	
	@EJB
	private AdvertenciaService advertenciaService;

	@EJB
	private EstoqueAtualServices estoqueAtualService;

	@EJB
	private RelatorioService relatorioService;

	@EJB
	private QuilometragemBoyService quilometragemBoyService;

	@EJB
	private FechamentoCaixaService fechamentoCaixaService;

	@EJB
	private ValeFuncionarioService valeFuncionarioService;

	@EJB
	private DiariaFuncionarioService diariaFuncionarioService;

	@PersistenceContext
	private EntityManager manager;

	private Date dataInicio;
	private Date dataFim;
	private Long idCaixa;
	private Long idFuncionario;

	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

	/*
	 * public void pdf(String nomeJasper, String tituloRelatorio, String
	 * dataInicio, String dataFim, List<?> lista, HashMap<String, Object> hm)
	 * throws JRException, IOException {
	 * 
	 * JRBeanCollectionDataSource beanCollectionDataSource = new
	 * JRBeanCollectionDataSource(lista); String reportPath =
	 * FacesContext.getCurrentInstance
	 * ().getExternalContext().getRealPath("/relatorio/" + nomeJasper);
	 * hm.put("tituloRelatorio", tituloRelatorio); hm.put("periodo", "Periodo: "
	 * + dataInicio + " a " + dataFim); hm.put("REPORT_LOCALE", new Locale("pt",
	 * "BR")); hm.put("SUBREPORT_DIR",
	 * FacesContext.getCurrentInstance().getExternalContext
	 * ().getRealPath("/relatorio/")); JasperPrint jasperPrint =
	 * JasperFillManager.fillReport(reportPath,hm, beanCollectionDataSource);
	 * HttpServletResponse httpServletResponse = (HttpServletResponse)
	 * FacesContext.getCurrentInstance().getExternalContext().getResponse();
	 * httpServletResponse
	 * .addHeader("Content-disposition","attachment; filename=report.pdf");
	 * ServletOutputStream servletOutputStream =
	 * httpServletResponse.getOutputStream();
	 * JasperExportManager.exportReportToPdfStream
	 * (jasperPrint,servletOutputStream);
	 * FacesContext.getCurrentInstance().responseComplete(); }
	 */

	public void relatorioEstoqueAtual() {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			EstadoEstoque estadoEstoque = new EstadoEstoque();
			estadoEstoque.getEstoque().setId(1L);
			estadoEstoque = estoqueAtualService
					.recuperaEstadoAtual(estadoEstoque);

			List<EstoqueAtual> listaEstoqueAtual = new ArrayList<EstoqueAtual>();

			listaEstoqueAtual = estoqueAtualService
					.recuperaListaEstoqueAtual(estadoEstoque);

			hm.put("dataEstoque",
					formato.format(estadoEstoque.getDataEstoque()));
			String jasper = "relatorioEstoqueAtual.jasper";
			String titulo = "Relatório de Estoque Atual";

			relatorioService.pdf(jasper, titulo, "", "", listaEstoqueAtual, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void relatorioFuncionariosAtivos() {
		String jasper = "relatorioFuncionarioAtivo.jasper";
		String titulo = "Relatório de Funcionários Ativos";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			List<Funcionario> lista = new ArrayList<Funcionario>();
			Loja loja = new Loja();
			loja.setId(1L);
			lista = funcionarioService
					.listaFuncionarioPorLoja(loja, null, null);
			relatorioService.pdf(jasper, titulo, "", "", lista, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void relatorioGastosCaixa() {
		String jasper = "relatorioGastosCaixa.jasper";
		String titulo = "Relatório de Gasto dos Caixas";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("dataGastoCaixa", formato.format(new Date()));
		try {
			List<GastoCaixa> lista = new ArrayList<GastoCaixa>();
			Loja loja = new Loja();
			loja.setId(1L);
			GastoCaixaVO gastoVO = new GastoCaixaVO();
			gastoVO.setDatagastoCaixa(formato.format(new Date()));
			lista = gastoService.relatorioListaGastoPorPeriodo(getDataInicio(), getDataFim());
			relatorioService.pdf(jasper, titulo, "", "", lista, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void gerarAdv() {
		Advertencia adv = new Advertencia();
		TypedQuery<Advertencia> query = this.manager.createQuery(
				"select x from Advertencia x where x.id=1", Advertencia.class);
		adv = query.getSingleResult();
		String jasper = "advertencia.jasper";
		String titulo = "CARTA DE ADVERTÊNCIA DISCIPLINAR";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("adv", "");
		try {
			List<AdvertenciaVO> advVO = new ArrayList<AdvertenciaVO>();
			AdvertenciaVO advertenciaVO = new AdvertenciaVO();
			List<AdvertenciaMotivo> lista = new ArrayList<AdvertenciaMotivo>();
			lista = advertenciaService.listaMotivos(adv);
			advertenciaVO.setAdv(adv);
			advertenciaVO.setLintaMotivo(lista);
			advVO.add(advertenciaVO);
			relatorioService.pdf(jasper, titulo, "", "", advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// TODO Auto-generated method stub

	}

	
	public void relatorioGastosNoMes() {
		// TODO Auto-generated method stub
		String jasper = "relatorioTotalPorTipoGasto.jasper";
		String titulo = "GASTOS CONSOLIDADO";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		 String periodo = "De "+ formato.format(getDataInicio()) + " até " +
		 formato.format(getDataFim()); hm.put("periodo",periodo);
		 hm.put("periodo", periodo);
		try {
			List<RelGastoVO> advVO = new ArrayList<RelGastoVO>();
			RelGastoVO relGastoVO = new RelGastoVO();
			List<GastoVO>  totalGastoPorTipoConta;
			totalGastoPorTipoConta = contaService.totalGastoPorTipoConta(getDataInicio(), getDataFim());
			GastoVO gastoVO = new GastoVO();
			if(totalGastoPorTipoConta!=null){
				relGastoVO.setTotalGastoPorTipoConta(totalGastoPorTipoConta);
				gastoVO.setTotal(relGastoVO.getTotalGastoPorTipoConta().get(0).getTotal());
			}			
			relGastoVO.setGastoVO(gastoVO);
			advVO.add(relGastoVO);
			relatorioService.pdf(jasper, titulo, formato.format(getDataInicio()), formato.format(getDataFim()), advVO, hm);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	

	
	
	public void relatorioGastosNoMesDetalhada() {
		// TODO Auto-generated method stub
		String jasper = "relatorioTotalPorTipoGastoDetalhada.jasper";
		String titulo = "GASTOS CONSOLIDADO";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		
		 String periodo = "De "+ formato.format(getDataInicio()) + " até " +
		 formato.format(getDataFim()); hm.put("periodo",periodo);
		 hm.put("periodo", periodo);
		try {
			List<RelGastoVO> advVO = new ArrayList<RelGastoVO>();
			RelGastoVO relGastoVO = new RelGastoVO();
			List<GastoVO>  totalGastoPorTipoConta;
			totalGastoPorTipoConta = contaService.totalGastoPorTipoConta(getDataInicio(), getDataFim());
			GastoVO gastoVO = new GastoVO();
			if(totalGastoPorTipoConta!=null){
				relGastoVO.setTotalGastoPorTipoConta(totalGastoPorTipoConta);
				gastoVO.setTotal(relGastoVO.getTotalGastoPorTipoConta().get(0).getTotal());
			}			
			relGastoVO.setGastoVO(gastoVO);
			advVO.add(relGastoVO);
			relatorioService.pdf(jasper, titulo, formato.format(getDataInicio()), formato.format(getDataFim()), advVO, hm);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public void caixaConcolidado() {
		String jasper = "fechametoConsolidado.jasper";
		String titulo = "FECHAMENTO CONSOLIDADO";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		/*
		 * String periodo = "De "+ formato.format(getDataInicio()) + " até " +
		 * formato.format(getDataFim()); hm.put("periodo",periodo);
		 */
		try {
			List<CaixaConsolidadoVO> advVO = new ArrayList<CaixaConsolidadoVO>();
			CaixaConsolidadoVO caixaConsolidadoVO = new CaixaConsolidadoVO();
			caixaConsolidadoVO = populaObjeto(caixaConsolidadoVO);
			caixaConsolidadoVO = populaGastosConsolidadoParaPeriodo(caixaConsolidadoVO);
			caixaConsolidadoVO = populaGasolinaConsolidadoParaPeriodo(caixaConsolidadoVO);
			caixaConsolidadoVO = populaValesConsolidadoParaPeriodo(caixaConsolidadoVO);
			caixaConsolidadoVO.setResultado(new Conversoes().converteDoubleToString(Double.parseDouble(caixaConsolidadoVO.getTotalDeVendas()
									.replaceAll(",", "."))
							- Double.parseDouble(caixaConsolidadoVO
									.getTotalDeGastos().replaceAll(",", "."))));
			advVO.add(caixaConsolidadoVO);
			relatorioService.pdf(jasper, titulo,
					formato.format(getDataInicio()),
					formato.format(getDataFim()), advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// TODO Auto-generated method stub

	}

	private CaixaConsolidadoVO populaValesConsolidadoParaPeriodo(
			CaixaConsolidadoVO caixaConsolidadoVO) {
		double total = 0;

		List<Valefuncionario> lista = valeFuncionarioService
				.getValefuncionariosPorPeriodo(getDataInicio(), getDataFim());

		for (Valefuncionario v : lista) {
			total += Double.parseDouble(v.getValor().replaceAll(",", "."));
		}
		caixaConsolidadoVO.setVales(new Conversoes()
				.converteDoubleToString(total));
		double totalGasto = Double.parseDouble(caixaConsolidadoVO
				.getTotalDeGastos().replaceAll(",", "."));
		totalGasto += total;
		caixaConsolidadoVO.setTotalDeGastos(new Conversoes()
				.converteDoubleToString(totalGasto));

		return caixaConsolidadoVO;
	}

	private CaixaConsolidadoVO populaGasolinaConsolidadoParaPeriodo(
			CaixaConsolidadoVO caixaConsolidadoVO) {
		List<QuilometroMotoBoy> lista = quilometragemBoyService
				.listaQuilometroMotoPorPeriodo(getDataInicio(), getDataFim());
		double total = 0;

		for (QuilometroMotoBoy q : lista) {
			total += Double.parseDouble(q.getValorPago().replaceAll(",", "."));
		}

		caixaConsolidadoVO.setGasolina(new Conversoes()
				.converteDoubleToString(total));

		double totalGasto = Double.parseDouble(caixaConsolidadoVO
				.getTotalDeGastos().replaceAll(",", "."));
		totalGasto += total;
		caixaConsolidadoVO.setTotalDeGastos(new Conversoes()
				.converteDoubleToString(totalGasto));

		return caixaConsolidadoVO;
	}

	private CaixaConsolidadoVO populaGastosConsolidadoParaPeriodo(
			CaixaConsolidadoVO caixaConsolidadoVO) {
		List<GastoCaixa> listaGasto = gastoService
				.relatorioListaGastoPorPeriodo(getDataInicio(), getDataFim());
		double total = 0;
		for (GastoCaixa g : listaGasto) {
			total += g.getValor();
		}

		caixaConsolidadoVO.setGastos(new Conversoes()
				.converteDoubleToString(total));

		double totalGasto = Double.parseDouble(caixaConsolidadoVO
				.getTotalDeGastos().replaceAll(",", "."));
		totalGasto += total;
		caixaConsolidadoVO.setTotalDeGastos(new Conversoes().converteDoubleToString(totalGasto));
		return caixaConsolidadoVO;
	}

	public CaixaConsolidadoVO populaObjeto(CaixaConsolidadoVO caixaConsolidado) {
		List<Fechamentocaixa> fechamento = fechamentoCaixaService
				.getFechamentoCaixasPorDataInicioFim(getDataInicio(),
						getDataFim());
		double total_dinheiro = 0;
		double total_debito = 0;
		double total_credito = 0;
		double total_contaCliente = 0;
		double total_geral = 0;

		for (Fechamentocaixa f : fechamento) {
			double totalDinheiro = Double.parseDouble(f.getDinheiro()
					.replaceAll(",", "."));
			double totalDebito = Double.parseDouble(f.getDebito().replaceAll(
					",", "."));
			double totalCredito = Double.parseDouble(f.getCredito().replaceAll(
					",", "."));
			double totalContaCliente = Double.parseDouble(f.getContaCliente()
					.replaceAll(",", "."));
			total_dinheiro += totalDinheiro;
			total_debito += totalDebito;
			total_credito += totalCredito;
			total_contaCliente += totalContaCliente;
			total_geral += (totalDinheiro + totalDebito + totalCredito + totalContaCliente);
		}
		caixaConsolidado.setDinheiro(new Conversoes()
				.converteDoubleToString(total_dinheiro));
		caixaConsolidado.setDebito(new Conversoes()
				.converteDoubleToString(total_debito));
		caixaConsolidado.setCredito(new Conversoes()
				.converteDoubleToString(total_credito));
		caixaConsolidado.setContaCliente(new Conversoes()
				.converteDoubleToString(total_contaCliente));
		caixaConsolidado.setTotalDeVendas(new Conversoes()
				.converteDoubleToString(total_geral));

		return caixaConsolidado;
	}

	public void fechamentoPorCaixa() {
		String jasper = "relatorioFechamentoPCaixa.jasper";
		String titulo = "FECHAMENTO POR CAIXA";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			List<FechamentoPorCaixaVO> advVO = new ArrayList<FechamentoPorCaixaVO>();
			Fechamentocaixa fechamento = new Fechamentocaixa();
			List<GastoCaixa> listaGastoCaixa = new ArrayList<GastoCaixa>();
			List<QuilometroMotoBoy> listaMotoBoyCaixa = new ArrayList<QuilometroMotoBoy>();
			List<Valefuncionario> listaValeCaixa = new ArrayList<Valefuncionario>();
			List<Diariafuncionario> listaDiaria = new ArrayList<Diariafuncionario>();

			Caixa caixa = new Caixa();
			caixa.setId(getIdCaixa());
			listaGastoCaixa = gastoService.listaGastosPorCaixa(caixa,
					getDataInicio());
			listaMotoBoyCaixa = quilometragemBoyService
					.listaQuilometroMotoBoyPorCaixa(caixa, getDataInicio());
			listaValeCaixa = valeFuncionarioService.listasValePorCaixa(caixa, getDataInicio());
			listaDiaria = diariaFuncionarioService.listasDiariaPorCaixa(caixa, getDataInicio());

			FechamentoPorCaixaVO fechamentoPorCaixaVO = new FechamentoPorCaixaVO();
			fechamentoPorCaixaVO.setListaGastoCaixa(listaGastoCaixa);
			fechamentoPorCaixaVO.setListaMotoBoyCaixa(listaMotoBoyCaixa);
			fechamentoPorCaixaVO.setListaValeCaixa(listaValeCaixa);
			fechamentoPorCaixaVO.setListaDiariaCaixa(listaDiaria);

			fechamento = fechamentoCaixaService.fechamentocaixaPorCaixa(getDataInicio(), caixa);
			if (fechamento != null) {
				fechamentoPorCaixaVO.setDinheiro(fechamento.getDinheiro());
				fechamentoPorCaixaVO.setDebito(fechamento.getDebito());
				fechamentoPorCaixaVO.setCredito(fechamento.getCredito());
				fechamentoPorCaixaVO.setContaCliente(fechamento.getContaCliente());
				fechamentoPorCaixaVO.setComandaRecebidaCancelada(fechamento.getComandaRecebidaCancelada());
				fechamentoPorCaixaVO.setAtendente(fechamento.getFuncionario().getNome());
				fechamentoPorCaixaVO.setTotalDeVendas(fechamento.getTotalVenda());
				fechamentoPorCaixaVO.setTotalDinheiro(totalDinheiro(listaGastoCaixa, listaMotoBoyCaixa, listaValeCaixa, listaDiaria, fechamento.getDinheiro()));
			}
			advVO.add(fechamentoPorCaixaVO);
			relatorioService.pdf(jasper, titulo, formato.format(getDataInicio()), "", advVO, hm);
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// TODO Auto-generated method stub

	}

	public void valeProFuncionario() {
		String jasper = "";
		String titulo ="";
			jasper = "relatorioDeVales.jasper";
			titulo = "Vales dos funcionários";
			HashMap<String, Object> hm = new HashMap<String, Object>();
			try {
				List<ValesFuncionariosVO> advVO = new ArrayList<ValesFuncionariosVO>();
				List<ValefuncionarioVO> listaVales1 = new ArrayList<ValefuncionarioVO>();
				List<Valefuncionario> listaVales = new ArrayList<Valefuncionario>();
				double total = 0;
				if(getIdFuncionario()==null || getIdFuncionario()<1){
					listaVales1 = valeFuncionarioService.getTodosValefuncionariosPorFuncionarioData(getDataInicio(), getDataFim());
					for (ValefuncionarioVO vale : listaVales1) {
						ValesFuncionariosVO valesFuncionariosVO = new ValesFuncionariosVO();
						valesFuncionariosVO.setNomeFuncionario(vale.getValeFuncionario().getFuncionario().getNome());
						valesFuncionariosVO.setCaixa("");
						valesFuncionariosVO.setUsuarioLiberou("");
						valesFuncionariosVO.setValor(vale.getValeFuncionario().getValor());
						valesFuncionariosVO.setData("");
						total += Double.parseDouble(vale.getValeFuncionario().getValor()
								.replaceAll(",", "."));
						advVO.add(valesFuncionariosVO);
					}
				}else{
					listaVales = valeFuncionarioService.getValefuncionariosPorFuncionarioData(idFuncionario,getDataInicio(), getDataFim());
					for (Valefuncionario vale : listaVales) {
						ValesFuncionariosVO valesFuncionariosVO = new ValesFuncionariosVO();
						valesFuncionariosVO.setNomeFuncionario(vale.getFuncionario()
								.getNome());
						valesFuncionariosVO.setCaixa(vale.getCaixa().getDescricao());
						valesFuncionariosVO.setUsuarioLiberou(vale.getUsuario()
								.getNome());
						valesFuncionariosVO.setValor(vale.getValor());
						valesFuncionariosVO.setData(new Conversoes().formato
								.format(vale.getDataVale()));
						total += Double.parseDouble(vale.getValor()
								.replaceAll(",", "."));
						advVO.add(valesFuncionariosVO);
					}
				}
				String totalVale = new Conversoes().converteDoubleToString(total);
				hm.put("totalVale", totalVale);
				relatorioService.pdf(jasper, titulo,
						formato.format(getDataInicio()), "", advVO, hm);
			} catch (JRException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}// TODO Auto-generated method stub

	}

	private String totalDinheiro(List<GastoCaixa> listaGastoCaixa,
			List<QuilometroMotoBoy> listaMotoBoyCaixa,
			List<Valefuncionario> listaValeCaixa, List<Diariafuncionario> listaDiaria, String dinheiro) {
		double totalDinheiro = 0;
		double dinheiroAtual = Double
				.parseDouble(dinheiro.replaceAll(",", "."));
		String retorno;
		for (GastoCaixa g : listaGastoCaixa) {
			totalDinheiro += g.getValor();
		}
		for (QuilometroMotoBoy k : listaMotoBoyCaixa) {
			totalDinheiro += Double.parseDouble(k.getValorPago().replaceAll(
					",", "."));
		}
		for (Valefuncionario v : listaValeCaixa) {
			totalDinheiro += Double.parseDouble(v.getValor().replaceAll(",", "."));
		}
		
		for (Diariafuncionario d : listaDiaria) {
			totalDinheiro += Double.parseDouble(d.getValor().replaceAll(",", "."));
		}

		dinheiroAtual -= totalDinheiro;

		retorno = new Conversoes().converteDoubleToString(dinheiroAtual);
		return retorno;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Long getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

}
