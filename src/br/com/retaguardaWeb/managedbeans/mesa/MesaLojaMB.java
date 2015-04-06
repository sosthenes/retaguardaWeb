package br.com.retaguardaWeb.managedbeans.mesa;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.MesaLoja;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.sessionbeans.MesaLojaEJB;

@ViewScoped
@ManagedBean(name="mesaLojaMB")
public class MesaLojaMB extends BasicoMB{

	@EJB
	private MesaLojaEJB repositorio ;
	
	private MesaLoja mesaLoja;
	private List<MesaLoja> listaMesaLoja;
	
	private List<MesaLoja> mesaSelecionada;
	
	
	@PostConstruct
	private void init() {
		if(mesaSelecionada==null)
			mesaSelecionada = new ArrayList<MesaLoja>();
		
		if(mesaLoja==null){
			mesaLoja = new MesaLoja();
		}
		if(listaMesaLoja==null || listaMesaLoja.size()<1){
			listar();
		}
	}
	 
	
	
	 public void adiciona () {
		 mesaLoja.setIdLoja(getLoja());
		 this.repositorio.adiciona (getMesaLoja());
		 limpar();
		 listar();
		 retornaMensagemSucessoOperacao();
	 }
	

	public MesaLoja getMesaLoja() {
		return mesaLoja;
	}

	public void setMesaLoja(MesaLoja mesaLoja) {
		this.mesaLoja = mesaLoja;
	}




	@Override
	public void listar() {
		listaMesaLoja = this.repositorio.getMesaLojas(getLoja());
		
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		this.repositorio.remover(mesaLoja);
		limpar();
		retornaMensagemSucessoOperacao();
		listar();
	}

	@Override
	public void limpar() {
		 getMesaLoja().setDescricao(null);
		 getMesaLoja().setId(null);
		 getMesaLoja().setIdLoja(new Loja());
		 mesaLoja = new MesaLoja();
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}



	public List<MesaLoja> getListaMesaLoja() {
		return listaMesaLoja;
	}



	public void setListaMesaLoja(List<MesaLoja> listaMesaLoja) {
		this.listaMesaLoja = listaMesaLoja;
	}
	
	public List<MesaLoja> getMesaSelecionada() {
		return mesaSelecionada;
	}



	public void setMesaSelecionada(List<MesaLoja> mesaSelecionada) {
		this.mesaSelecionada = mesaSelecionada;
	}	

}