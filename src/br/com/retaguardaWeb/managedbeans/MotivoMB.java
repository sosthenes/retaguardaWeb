package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.Cargo;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Motivo;
import br.com.retaguardaWeb.services.MotivoService;

@Named
@ViewScoped
public class MotivoMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private MotivoService motivoService;
	
	private Motivo motivo= new Motivo();
	
	private Loja loja = new Loja();
	
	@Override
	public void adiciona() {
		if(motivo!=null){
			setLoja(getLoja());
			motivo.setIdLoja(loja);
			this.motivoService.adiciona (this.motivo);
		 this.motivo= new Motivo();
		 listar();
		}else{
			motivo = new Motivo();
		}		
	}

	@Override
	public void listar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	public Motivo getMotivo() {
		return motivo;
	}

	public void setMotivo(Motivo motivo) {
		this.motivo = motivo;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}


	 
	
	
}
