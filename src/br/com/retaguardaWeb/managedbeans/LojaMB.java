package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.services.LojaService;

@Named
@ViewScoped
public class LojaMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private LojaService lojaService;
	
	private List<Loja> lojas  = new ArrayList<Loja>();
	
	private Loja loja = new Loja();
	
	public void adiciona() {
		if(loja!=null){
			this.lojaService.adiciona (this.loja);
		 this.loja= new Loja();
		}else{
			loja = new Loja();
		}
	 }
	 
	public void remover() {
		lojaService.remover(loja);
	}
	
	public Loja getLoja() {
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}


	public List<Loja> getLojas() {
		lojas = lojaService.getLojas();
		return lojas;
	}


	public void setLojas(List<Loja> lojas) {
		this.lojas = lojas;
	}
	 
	

	 
	
	
}
