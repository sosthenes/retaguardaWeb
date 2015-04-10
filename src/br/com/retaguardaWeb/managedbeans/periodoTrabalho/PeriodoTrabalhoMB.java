package br.com.retaguardaWeb.managedbeans.periodoTrabalho;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.managedbeans.BasicoMB;
import br.com.retaguardaWeb.services.PeriodoTrabalhoService;

@Named
@RequestScoped
public class PeriodoTrabalhoMB extends BasicoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	@Inject
	private PeriodoTrabalhoService periodoService;
	
	public void iniciarNovoPeriodoDeTrabalho() {
		PeriodoTrabalho periodoTrabalho = new PeriodoTrabalho();
		periodoTrabalho.setDescricao("Abrir Per�odo");
		periodoTrabalho.setFuncionario(getUsuario().getFuncionario());
		periodoTrabalho.setHoraInicio(new Date());
		periodoTrabalho.setHoraFim(null);
		periodoTrabalho.setIdLoja(getLoja());
		periodoService.adiciona(periodoTrabalho);
		retornaMensagemSucesso("Per�odo aberto com sucesso!");
	}

	public void fecharPeriodoDeTrabalho() {
		periodoService.seUltimoPeriodoAberto(getLoja(), (ultimoPeriodo) -> {
			ultimoPeriodo.setHoraFim(new Date());
			if(!periodoService.verificaCaixaAberto(getLoja(),periodo, null)){
				periodoService.alterar(getPeriodo());
				retornaMensagemSucesso("Per�odo fechado com sucesso!");
			}else{
				retornaMensagemErro("Existe caixa aberto nesse per�odo, feche todos os caixa antes de encerrar o per�odo de trabalho!");
			}
		}); 
	}

	public boolean isPeriodoAberto() {
		PeriodoTrabalho ultimoPeriodo = periodoService.recuperaUltimoPeriodo(getLoja());
		return seUltimoPeriodoAberto(ultimoPeriodo);
	}
	
	private void seUltimoPeriodoAberto(PeriodoTrabalho ultimoPeriodo,Callback callback) {
		if(seUltimoPeriodoAberto(ultimoPeriodo)) {
			callback.run();
		}
	}
	
	
}
