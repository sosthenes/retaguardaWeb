package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.QuilometroMotoBoy;
import br.com.retaguardaWeb.entidades.Setor;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.QuilometragemBoyService;
import br.com.retaguardaWeb.util.Conversoes;

@Named
@ViewScoped
public class QuilometragemBoyMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private FuncionarioService funcionarioService;
	@EJB
	private QuilometragemBoyService kmBoyService;
	
	private List<Funcionario> funcionarioes  = new ArrayList<Funcionario>();
	List<QuilometroMotoBoy> listaKm = new ArrayList<QuilometroMotoBoy>();
	
	private Long idFuncionario;
	private Setor setor = new Setor();
	private QuilometroMotoBoy kmBoy;
	private String totalGasto;
	private Funcionario funcionario;
	private Long idCaixa;
	Caixa caixa = new Caixa();
	
	private Date data;
	
	@PostConstruct
	private void init() {
		listar();
	}
	
	
	@Override
	public void adiciona() {
		funcionario = new Funcionario();
		funcionario.setId(getIdFuncionario());
		kmBoy.setFuncionario(funcionario);
		kmBoy.setData(getData());
		kmBoy.setUsuario(getUsuario());
		getCaixa().setId(getIdCaixa());
		kmBoy.setCaixa(getCaixa());
		kmBoyService.adiciona(kmBoy);
		listar();
		limpar();
	}

	@Override
	public void listar() {
		QuilometroMotoBoy quilometroMotoBoy = new QuilometroMotoBoy();
		if(getIdFuncionario()!=null){
			quilometroMotoBoy.setFuncionario(new Funcionario());
			quilometroMotoBoy.getFuncionario().setId(getIdFuncionario());
		}
		setListaKm(kmBoyService.pesquisaKmsPorId(quilometroMotoBoy));
	}

	@Override
	public void remover() {
		kmBoyService.remover(getKmBoy());
		listar();
		limpar();
	}

	@Override
	public void limpar() {
		setIdFuncionario(null);
		setKmBoy(new QuilometroMotoBoy());
		data = null;
	}


	public void listaFuncionarioes() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		lista = funcionarioService.listaFuncionarioPorLoja(null, null, null);
		if(!lista.isEmpty()){
			funcionarioes = lista;
		}
	}

	public List<Funcionario> getFuncionarioes() {
		if(funcionarioes==null || funcionarioes.isEmpty())
			listaFuncionarioes();
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

	public QuilometroMotoBoy getKmBoy() {
		if(kmBoy ==null)
			kmBoy = new QuilometroMotoBoy();
		return kmBoy;
	}

	public void setKmBoy(QuilometroMotoBoy kmBoy) {
		this.kmBoy = kmBoy;
	}

	public List<QuilometroMotoBoy> getListaKm() {
		if(listaKm==null)
			listar();
		return listaKm;
	}

	public void setListaKm(List<QuilometroMotoBoy> listaKm) {
		this.listaKm = listaKm;
	}

	public String getTotalGasto() {
		double total = 0.00;
		for(QuilometroMotoBoy gasto : getListaKm()){
			total+=Double.parseDouble(gasto.getValorPago().replaceAll(",", "."));
		}
		totalGasto = new Conversoes().converteDoubleToString(total);
		return totalGasto;
	}

	public void setTotalGasto(String totalGasto) {
		this.totalGasto = totalGasto;
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

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	public Long getIdCaixa() {
		return idCaixa;
	}

	public void setIdCaixa(Long idCaixa) {
		this.idCaixa = idCaixa;
	}

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}
	
	public void gerarRecibo() {
		kmBoyService.gerarRecibo(kmBoy);
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
}
