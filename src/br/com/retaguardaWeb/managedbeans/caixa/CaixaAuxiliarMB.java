package br.com.retaguardaWeb.managedbeans.caixa;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.sessionbeans.CaixaService;

@ViewScoped
@ManagedBean(name="caixaAuxiliarMB")
public class CaixaAuxiliarMB  {

	
	@EJB
	private CaixaService caixaService;

	
	
	@PostConstruct
	public void init() {
		
		
	}



	public CaixaService getCaixaService() {
		return caixaService;
	}



	public void setCaixaService(CaixaService caixaService) {
		this.caixaService = caixaService;
	}
	
	

}
