package br.com.retaguardaWeb.managedbeans.periodoTrabalho;

import javax.inject.Named;
import java.io.Serializable;
import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.services.PeriodoTrabalhoService;

@Named
@ViewScoped
public class PeriodoTrabalhoMB extends BasicoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	PeriodoTrabalho periodoTrabalho = new PeriodoTrabalho();
	
	@Inject
	private PeriodoTrabalhoService periodoService;
	
	private PeriodoTrabalho periodo;
	private boolean periodoAberto;
	
	@PostConstruct
	public void init(){
		isPeriodoAberto();
	}
	
	@Override
	public void adiciona() {
		periodoTrabalho.setDescricao("Abrir Per�odo");
		periodoTrabalho.setFuncionario(getUsuario().getFuncionario());
		periodoTrabalho.setHoraInicio(new Date());
		periodoTrabalho.setHoraFim(null);
		periodoTrabalho.setIdLoja(getLoja());
		periodoService.adiciona(periodoTrabalho);
		retornaMensagemSucesso("Per�odo aberto com sucesso!");
	}

	@Override
	public void listar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover() {
		getPeriodo().setHoraFim(new Date());
		if(!periodoService.verificaCaixaAberto(getLoja(),periodo, null)){
			periodoService.alterar(getPeriodo());
			retornaMensagemSucesso("Per�odo fechado com sucesso!");
		}else{
			retornaMensagemErro("Existe caixa aberto nesse per�odo, feche todos os caixa antes de encerrar o per�odo de trabalho!");
		}
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}


	public PeriodoTrabalho getPeriodo() {
		if(periodo==null || periodo.getId()==null)
			periodo = periodoService.recuperaUltimoPeriodo(getLoja());
		return periodo;
	}

	public boolean isPeriodoAberto() {
		if(getPeriodo()!=null && getPeriodo().getHoraFim()==null){
			periodoAberto = true; 
		}else{
			setPeriodo(null);
			periodoAberto = false;
		}
		return periodoAberto;
	}

	public void setPeriodo(PeriodoTrabalho periodo) {
		this.periodo = periodo;
	}
	
	
}
