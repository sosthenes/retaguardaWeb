package br.com.retaguardaWeb.managedbeans.periodoTrabalho;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.sessionbeans.PeriodoTrabalhoService;

@ViewScoped
@ManagedBean(name="periodoTrabalhoMB")
public class PeriodoTrabalhoMB extends BasicoMB{

	PeriodoTrabalho periodoTrabalho = new PeriodoTrabalho();
	
	@EJB
	public PeriodoTrabalhoService periodoService;
	
	private PeriodoTrabalho periodo;
	private boolean periodoAberto;
	
	@PostConstruct
	public void init(){
		isPeriodoAberto();
	}
	
	@Override
	public void adiciona() {
		periodoTrabalho.setDescricao("Abrir Período");
		periodoTrabalho.setFuncionario(getUsuario().getFuncionario());
		periodoTrabalho.setHoraInicio(new Date());
		periodoTrabalho.setHoraFim(null);
		periodoTrabalho.setIdLoja(getLoja());
		periodoService.adiciona(periodoTrabalho);
		retornaMensagemSucesso("Período aberto com sucesso!");
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
			retornaMensagemSucesso("Período fechado com sucesso!");
		}else{
			retornaMensagemErro("Existe caixa aberto nesse período, feche todos os caixa antes de encerrar o período de trabalho!");
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
