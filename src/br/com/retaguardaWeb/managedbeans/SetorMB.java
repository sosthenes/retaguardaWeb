package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.services.SetorService;

@ManagedBean
@ViewScoped
public class SetorMB extends BasicoMB{

	@EJB
	private SetorService setorService;
	
	private List<Setor> setores  = new ArrayList<Setor>();
	
	private Setor setor = new Setor();
	
	
	@PostConstruct
	private void init() {
		listaSetores();
	
	}
	
	public void adiciona () {
		if(setor!=null){
			setor.setIdLoja(getLoja());
			this.setorService.adiciona (this.setor);
		 this.setor= new Setor();
		 listaSetores();
		}else{
			setor = new Setor();
		}
	 }
	 
	public void listaSetores() {
		List<Setor> lista = new ArrayList<Setor>();
		lista = setorService.listaSetorPorLoja(getLoja());
		setores = lista;
	}
	
	public void remover() {
		setorService.remover(setor);
		listaSetores();
	}
	
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}


	public List<Setor> getSetores() {
		return setores;
	}



	public void setSetores(List<Setor> setores) {
		this.setores = setores;
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
