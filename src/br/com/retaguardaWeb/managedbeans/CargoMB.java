package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.services.CargoService;
import br.com.retaguardaWeb.services.LojaService;

@Named
@ViewScoped
public class CargoMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private CargoService cargoService;
	@EJB
	private LojaService lojaService;
	private List<Cargo> cargoes  = new ArrayList<Cargo>();
	private Cargo cargo = new Cargo();
	
	
	public void adiciona () {
		if(cargo!=null){
			cargo.setIdLoja(getLoja());
			this.cargoService.adiciona (this.cargo);
		 this.cargo= new Cargo();
		 listaCargoes();
		}else{
			cargo = new Cargo();
		}
	 }
	 
	public void listaCargoes() {
		List<Cargo> lista = new ArrayList<Cargo>();
		lista = cargoService.listaCargoPorLoja(getLoja());
		if(!lista.isEmpty()){
			getCargoes().addAll(lista);
		}
	}
	
	public void remover() {
		cargoService.remover(cargo);
		listaCargoes();
	}
	
	public Cargo getCargo() {
		return cargo;
	}
	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}


	public List<Cargo> getCargoes() {
		return cargoes;
	}



	public void setCargoes(List<Cargo> cargoes) {
		this.cargoes = cargoes;
	}

	@Override
	public void listar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}


	 
	

	 
	
	
}
