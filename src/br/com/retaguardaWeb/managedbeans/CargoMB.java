package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import sun.net.www.content.audio.basic;
import sun.security.action.GetLongAction;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.sessionbeans.CargoService;
import br.com.retaguardaWeb.sessionbeans.LojaService;

@ManagedBean
@ViewScoped
public class CargoMB extends BasicoMB{

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
