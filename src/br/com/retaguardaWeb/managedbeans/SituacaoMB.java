package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.SituacaoFuncional;
import br.com.retaguardaWeb.sessionbeans.SituacaoService;

@ManagedBean
@ViewScoped
public class SituacaoMB extends BasicoMB{

	@EJB
	private SituacaoService situacaoService;
	
	private List<SituacaoFuncional> situacaoes  = new ArrayList<SituacaoFuncional>();
	
	private SituacaoFuncional situacao = new SituacaoFuncional();
	
	
	public void adiciona () {
		if(situacao!=null){
			situacao.setIdLoja(getLoja());
			this.situacaoService.adiciona (this.situacao);
		 this.situacao= new SituacaoFuncional();
		 listaSituacaoes();
		}else{
			situacao = new SituacaoFuncional();
		}
	 }
	 
	public void listaSituacaoes() {
		List<SituacaoFuncional> lista = new ArrayList<SituacaoFuncional>();
		lista = situacaoService.listaSituacaoPorLoja(getLoja());
		if(!lista.isEmpty()){
			situacaoes = lista;
		}
	}
	
	public void remover() {
		situacaoService.remover(situacao);
		listaSituacaoes();
	}
	
	public SituacaoFuncional getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoFuncional situacao) {
		this.situacao = situacao;
	}


	public List<SituacaoFuncional> getSituacaoes() {
		return situacaoes;
	}



	public void setSituacaoes(List<SituacaoFuncional> situacaoes) {
		this.situacaoes = situacaoes;
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
