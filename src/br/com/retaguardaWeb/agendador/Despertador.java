package br.com.retaguardaWeb.agendador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.services.ContaService;
import br.com.retaguardaWeb.services.RelatorioService;
import br.com.retaguardaWeb.util.Conversoes;
import br.com.retaguardaWeb.vo.ContasAPagarVO;

//@Stateless
//@Startup
public class Despertador {

	
	/*@EJB
	private ContaService contaService;
	@EJB
	private RelatorioService relatorioService;	
	
	
	
	private Pagamento pagamento;
	
	@Schedule(hour = "8")
    public void executarTarefa() throws ServletException{
 
        System.out.println("Executando...");
       // relatorioFuncionariosAtivos();
    }

    
	public void relatorioFuncionariosAtivos() throws ServletException {
		String jasper = "relatorioContasParaHoje.jasper";
		String titulo = "Relat�rio de Contas a Pagar";
		HashMap<String, Object> hm = new HashMap<String, Object>();
		try {
			List<ParcelaPagamento> listaConsulta = new ArrayList<ParcelaPagamento>();
			List<ContasAPagarVO> lista = new ArrayList<ContasAPagarVO>();
			listaConsulta = contaService.contasDoDiaAPagar();
			
			if (listaConsulta != null && !listaConsulta.isEmpty()) {
				for(ParcelaPagamento lis : listaConsulta){
					ContasAPagarVO contasAPagar = new ContasAPagarVO();
					contasAPagar.setDataParcela(lis.getMyFormattedDate());
					contasAPagar.setDescricao(lis.getPagamento().getDescricao());
					contasAPagar.setNumParcela(lis.getNumParcela().toString());
					contasAPagar.setValorParcela(new Conversoes().converteDoubleToString(lis.getValorParcela()));
					lista.add(contasAPagar);
				}
				

				MailInformation enviaEmail = new MailInformation();
				HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				enviaEmail.doGet(req, res,pdfEmail(jasper, titulo, "", "", lista, hm));
			}
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    
	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}


    
    
	public byte[] pdfEmail(String nomeJasper, String tituloRelatorio, String dataInicio, String dataFim, List<?> lista, HashMap<String, Object> hm) throws JRException, IOException {  
	       
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lista);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/relatorio/" + nomeJasper);
		hm.put("tituloRelatorio", tituloRelatorio);
		if((dataInicio!=null && !dataInicio.equals("")) && (dataFim==null || dataFim.equals(""))){
			hm.put("periodo", "Data: " + dataInicio);
		}else{
			hm.put("periodo", "Periodo: " + dataInicio + " a " + dataFim);
		}
		hm.put("REPORT_LOCALE", new Locale("pt", "BR"));
		hm.put("SUBREPORT_DIR", FacesContext.getCurrentInstance().getExternalContext().getRealPath("/relatorio/")+"/");
	//	JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,hm, beanCollectionDataSource);
		
		byte[] pdf = JasperRunManager.runReportToPdf(reportPath, hm,beanCollectionDataSource);
		return pdf;
  }  
    */
}
