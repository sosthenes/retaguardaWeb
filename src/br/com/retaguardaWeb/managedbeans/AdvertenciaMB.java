package br.com.retaguardaWeb.managedbeans;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.TypedQuery;

import net.sf.jasperreports.engine.JRException;
import br.com.retaguardaWeb.entidades.Advertencia;
import br.com.retaguardaWeb.entidades.AdvertenciaMotivo;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.GastoCaixa;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Motivo;
import br.com.retaguardaWeb.entidades.QuilometroMotoBoy;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.sessionbeans.AdvertenciaService;
import br.com.retaguardaWeb.sessionbeans.FuncionarioService;
import br.com.retaguardaWeb.sessionbeans.MotivoService;
import br.com.retaguardaWeb.sessionbeans.RelatorioService;
import br.com.retaguardaWeb.vo.AdvertenciaVO;
import br.com.retaguardaWeb.vo.GastoCaixaVO;

@ManagedBean
@ViewScoped
public class AdvertenciaMB extends BasicoMB{

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private MotivoService motivoService;
	@EJB
	private AdvertenciaService advertenciaService;
	@EJB
	private RelatorioService relatorioService;
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	private List<Motivo> listaMotivo = new ArrayList<Motivo>();
	private List<AdvertenciaMotivo> listaAdvMotivo = new ArrayList<AdvertenciaMotivo>();
	private List<Advertencia> listaAdv;
	
	private List<String> idMotivo;
	
	private Long idFuncionario;
	private Setor setor = new Setor();
	private Advertencia adv;
	private Funcionario funcionario;
	
	private Date dataFato;
	
	
	
	@Override
	public void adiciona() {
		funcionario = new Funcionario();
		funcionario.setId(getIdFuncionario());
		adv.setFuncionario(funcionario);
		adv.setData(new Timestamp(System.currentTimeMillis()));
		adv.setDataFato(getDataFato());
		adv = advertenciaService.adiciona(adv, idMotivo);
		listar();
		limpar();
		retornaMensagemSucessoOperacao();
	}

	
	@Override
	public void listar() {
		listaAdv = advertenciaService.getAdvertencias();
	}

	@Override
	public void remover() {
		advertenciaService.remover(getAdv());
		listar();
		limpar();
	}

	@Override
	public void limpar() {
		setIdFuncionario(null);
		adv = new Advertencia();
		idMotivo = null;
	}


	public void listaFuncionarioes() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		getSetor().setId(3L);
		lista = funcionarioService.listaFuncionarioPorLoja(null, null, null);
		if(!lista.isEmpty()){
			funcionarioes = lista;
		}
	}

	public List<Funcionario> getFuncionarioes() {
		if(funcionarioes==null || funcionarioes.isEmpty()){
			listaFuncionarioes();
		}
	
		return funcionarioes;
	}

	public void setFuncionarioes(List<Funcionario> funcionarioes) {
		this.funcionarioes = funcionarioes;
	}

	public Setor getSetor() {
		if(setor==null){
			setor = new Setor();
		}
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}



	public Advertencia getAdv() {
		if(adv==null)
			adv = new Advertencia();
		return adv;
	}

	public void setAdv(Advertencia adv) {
		this.adv = adv;
	}

	public List<Motivo> getListaMotivo() {
		if(listaMotivo==null || listaMotivo.isEmpty()){
			listaMotivo = motivoService.getMotivos();
		}
		return listaMotivo;
	}

	public void setListaMotivo(List<Motivo> listaMotivo) {
		this.listaMotivo = listaMotivo;
	}



	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public List<String> getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(List<String> idMotivo) {
		this.idMotivo = idMotivo;
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	public List<AdvertenciaMotivo> getListaAdvMotivo() {
		return listaAdvMotivo;
	}

	public void setListaAdvMotivo(List<AdvertenciaMotivo> listaAdvMotivo) {
		this.listaAdvMotivo = listaAdvMotivo;
	}

	public List<Advertencia> getListaAdv() {
		if(listaAdv==null){
			listar();
		}
		return listaAdv;
	}

	public void setListaAdv(List<Advertencia> listaAdv) {
		this.listaAdv = listaAdv;
	}
	
	
	
	public void gerarAdv() {
		advertenciaService.gerarAdv(adv);
	}

	public Date getDataFato() {
		return dataFato;
	}

	public void setDataFato(Date dataFato) {
		this.dataFato = dataFato;
	}



	
}
