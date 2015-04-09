package br.com.retaguardaWeb.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Stateless
public class RelatorioService {

	
	@PersistenceContext
	private EntityManager manager;

	
	public void pdf(String nomeJasper, String tituloRelatorio, String dataInicio, String dataFim, List<?> lista, HashMap<String, Object> hm) throws JRException, IOException {  
	       
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
		JasperPrint jasperPrint = JasperFillManager.fillReport(reportPath,hm, beanCollectionDataSource);
		HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		httpServletResponse.addHeader("Content-disposition","attachment; filename=report.pdf");
		ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
		FacesContext.getCurrentInstance().responseComplete();
  }  
   
	
   

	
}
