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

import br.com.retaguardaWeb.entidades.DescricaoPagamento;
import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.Periodicidade;
import br.com.retaguardaWeb.entidades.TipoDePagamento;
import br.com.retaguardaWeb.services.ContaService;
import br.com.retaguardaWeb.services.DescricaoPagamentoService;
import br.com.retaguardaWeb.services.FormaPagamentoService;
import br.com.retaguardaWeb.services.ParcelaService;
import br.com.retaguardaWeb.services.PeriodicidadeService;
import br.com.retaguardaWeb.services.TipoDepagamentoService;
import br.com.retaguardaWeb.util.Conversoes;

@Named
@ViewScoped
public class PagamentoMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	
	private Long idTipoPagamento;
	private Long idDescricaoConta;
	
	@EJB
	private PeriodicidadeService periodicidadeService;
	@EJB
	private TipoDepagamentoService tipoPagamentoService;
	@EJB
	private ContaService contaService;
	@EJB
	private FormaPagamentoService formaPagamentoService;
	
	@EJB
	private ParcelaService parcelaService;
	@EJB
	private DescricaoPagamentoService descricaoPagamentoService;

	
	private List<FormaPagamento> listaFormapagamento;
	private FormaPagamento formapagamento;
	
	
	private TipoDePagamento tipoConta;
	private List<TipoDePagamento> listaTipoConta;
	private Periodicidade periodicidade;
	private List<Periodicidade> listaPeriodicidade;
	private Long idPeriodicidade;
	private Pagamento conta;
	private Date dataInicio;
	private Date datapagamentoParcela;
	private Date dataFim;
	private List<Pagamento> listaConta;
	private List<ParcelaPagamento> listaParcela;
	private List<ParcelaPagamento> listaParcelaDetalhada;
	private ParcelaPagamento parcela;
	
	private List<DescricaoPagamento> listaDescricaoPagamento;
	private DescricaoPagamento descricaoPagamento;
	
	private boolean carregaLista = false;
	private boolean pago;
	
	private List<ParcelaPagamento> listaParcelaAtrasada;
	
	
	
	@PostConstruct
	private void init() {
		listaPeriodicidade = periodicidadeService.getPeriodicidades();
		listaTipoConta = tipoPagamentoService.getTipoDePagamentos(getLoja());
		
		
		if(listaFormapagamento==null || listaFormapagamento.isEmpty())
			listarFormaPagamento();
		
		if(listaDescricaoPagamento==null || listaDescricaoPagamento.isEmpty())
			listarDescricaoPagamento();
		
		
		
		
	}
	
	private void listarFormaPagamento() {
		listaFormapagamento = new ArrayList<FormaPagamento>();
		listaFormapagamento = formaPagamentoService.listaFormaPagamento();
	}

	@Override
	public void adiciona() {
		conta.setIdLoja(getLoja());
		tipoConta = new TipoDePagamento();
		tipoConta.setId(getIdTipoPagamento());
		conta.setTipoPagamento(tipoConta);
		conta.setDataCompra(getDataInicio());
		conta.setDataFim(getDataFim());
		periodicidade = new  Periodicidade();
		periodicidade.setId(getIdPeriodicidade());
		conta.setPeriodocidade(getPeriodicidade());
		descricaoPagamento.setId(getIdDescricaoConta());
		conta.setDescricaoPagamento(descricaoPagamento);
		conta.setFormaPagamento(getFormapagamento());
		boolean parcela = contaService.adicionaParcela(conta, isPago());
		retornaMensagemSucessoOperacao();
		listar();
		limpar();
	}


	public void adicionaDescriocaoGasto() {
		descricaoPagamento.setIdLoja(getLoja());
		descricaoPagamentoService.adiciona(descricaoPagamento);
		listarDescricaoPagamento();
	}

	
	public void carregaParcela(){
		parcelaService.pesquisaPorId(getParcela());
	}

	
	public void pagarParcela(){
		getParcela().setDataPagamento(getDatapagamentoParcela());
		getParcela().setPago(true);
		parcelaService.alterar(getParcela());
		if(verificaQuitacao()){
			parcela.getPagamento().setPago(true);
			parcela.getPagamento().setDataPagamento(getDatapagamentoParcela());
			contaService.alterar(parcela.getPagamento());
		}
		retornaMensagemSucessoOperacao();
		listaParcelasDetalhadas();
		pesquisar();
	}
	
	private boolean verificaQuitacao() {
		return parcelaService.verificaQuitacaoPorId(parcela);
	}

	public void pesquisar() {
		listaParcela = new ArrayList<ParcelaPagamento>();
		listaParcela = parcelaService.pesquisa(conta, getDataFim(), getDataInicio());
	}
	
	public void pesquisarAtrasados() {
		listaParcelaAtrasada = new ArrayList<ParcelaPagamento>();
		listaParcelaAtrasada = parcelaService.pesquisaContaAtrasada(conta);
	}
	
	
	@Override
	public void listar() {
		List<Pagamento> lista = contaService.listar(getDataInicio(), getDataFim(), getLoja());
		listaConta = new ArrayList<Pagamento>();
		for(Pagamento pagto : lista){
			Pagamento pag = new Pagamento();
			pag = pagto;
			pag.setParcelas(parcelaService.listarPorId(pagto));
			listaConta.add(pag);
		}
	}

	@Override
	public void remover() {
		contaService.removerParcelas(parcela);	
		retornaMensagemSucessoOperacao();
		
		listar();
		pesquisar();
		limpar();
	}

	@Override
	public void limpar() {
		tipoConta=null;
		conta=null;
		dataInicio = null;
		dataFim=null;
		periodicidade = new  Periodicidade();
		idPeriodicidade=null;
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	public Long getIdTipoPagamento() {
		if(idTipoPagamento!=null){
			tipoConta = new TipoDePagamento();
			tipoConta.setId(idTipoPagamento);
		}
		return idTipoPagamento;
	}

	public void setIdTipoPagamento(Long idTipoPagamento) {
		this.idTipoPagamento = idTipoPagamento;
	}

	public TipoDePagamento getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoDePagamento tipoConta) {
		this.tipoConta = tipoConta;
	}

	public List<TipoDePagamento> getListaTipoConta() {
		return listaTipoConta;
	}

	public void setListaTipoConta(List<TipoDePagamento> listaTipoConta) {
		this.listaTipoConta = listaTipoConta;
	}

	public TipoDepagamentoService getTipoPagamentoService() {
		return tipoPagamentoService;
	}

	public void setTipoPagamentoService(TipoDepagamentoService tipoPagamentoService) {
		this.tipoPagamentoService = tipoPagamentoService;
	}

	public Pagamento getConta() {
		if(conta==null){
			conta = new Pagamento();
		}
		return conta;
	}

	public void setConta(Pagamento conta) {
		this.conta = conta;
	}

	public Date getDataInicio() {
		/*if(dataInicio==null)
			dataInicio = new Date();*/
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		/*if(dataFim==null)
			dataFim = new Date();*/
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<Pagamento> getListaConta() {
		return listaConta;
	}

	public void setListaConta(List<Pagamento> listaConta) {
		this.listaConta = listaConta;
	}

	public Periodicidade getPeriodicidade() {
		return periodicidade;
	}

	public void setPeriodicidade(Periodicidade periodicidade) {
		this.periodicidade = periodicidade;
	}

	public List<Periodicidade> getListaPeriodicidade() {
		return listaPeriodicidade;
	}

	public void setListaPeriodicidade(List<Periodicidade> listaPeriodicidade) {
		this.listaPeriodicidade = listaPeriodicidade;
	}

	public Long getIdPeriodicidade() {
		return idPeriodicidade;
	}

	public void setIdPeriodicidade(Long idPeriodicidade) {
		this.idPeriodicidade = idPeriodicidade;
	}

	public List<ParcelaPagamento> getListaParcela() {
		return listaParcela;
	}

	public void listaParcelasDetalhadas() {
		if(parcela!=null && parcela.getPagamento()!=null && parcela.getPagamento().getId()!=null){
			listaParcelaDetalhada = parcelaService.listarPorId(parcela.getPagamento());
		}
	}

	
	public void setListaParcela(List<ParcelaPagamento> listaParcela) {
		this.listaParcela = listaParcela;
	}


	public ParcelaPagamento getParcela() {
		if(parcela==null){
			parcela = new ParcelaPagamento();
		}
		return parcela;
	}

	public void listarDescricaoPagamento(){
		listaDescricaoPagamento = descricaoPagamentoService.listar(getLoja());
	}
	
	public void setParcela(ParcelaPagamento parcela) {
		this.parcela = parcela;
	}

	public String getValorTotalConsultaPago() {
		double total = 0;
		if(listaParcela!=null && !listaParcela.isEmpty()){
			for(ParcelaPagamento tot : listaParcela){
				if(tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		if(listaParcelaAtrasada!=null && !listaParcelaAtrasada.isEmpty()){
			for(ParcelaPagamento tot : listaParcelaAtrasada){
				if(tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		return new Conversoes().converteDoubleToString(total);
	}

	public String getValorTotalConsultaAPagar() {
		double total = 0;
		if(listaParcela!=null && !listaParcela.isEmpty()){
			for(ParcelaPagamento tot : listaParcela){
				if(!tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		if(listaParcelaAtrasada!=null && !listaParcelaAtrasada.isEmpty()){
			for(ParcelaPagamento tot : listaParcelaAtrasada){
				if(!tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		return new Conversoes().converteDoubleToString(total);
	}

	public String getValorTotalConsultaPagoParcelas() {
		double total = 0;
		if(listaParcelaDetalhada!=null && !listaParcelaDetalhada.isEmpty()){
			for(ParcelaPagamento tot : listaParcelaDetalhada){
				if(tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		return new Conversoes().converteDoubleToString(total);
	}

	public String getValorTotalConsultaAPagarParcelas() {
		double total = 0;
		if(listaParcelaDetalhada!=null && !listaParcelaDetalhada.isEmpty()){
			for(ParcelaPagamento tot : listaParcelaDetalhada){
				if(!tot.isPago())
					total+=tot.getValorParcela();
			}
		}
		return new Conversoes().converteDoubleToString(total);
	}


	public Long getIdDescricaoConta() {
		return idDescricaoConta;
	}


	public void setIdDescricaoConta(Long idDescricaoConta) {
		this.idDescricaoConta = idDescricaoConta;
	}


	public List<DescricaoPagamento> getListaDescricaoPagamento() {
		return listaDescricaoPagamento;
	}


	public void setListaDescricaoPagamento(
			List<DescricaoPagamento> listaDescricaoPagamento) {
		this.listaDescricaoPagamento = listaDescricaoPagamento;
	}


	public DescricaoPagamento getDescricaoPagamento() {
		if(descricaoPagamento==null)
			descricaoPagamento = new DescricaoPagamento();
		return descricaoPagamento;
	}


	public void setDescricaoPagamento(DescricaoPagamento descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}

	public List<ParcelaPagamento> getListaParcelaDetalhada() {
		return listaParcelaDetalhada;
	}

	public void setListaParcelaDetalhada(
			List<ParcelaPagamento> listaParcelaDetalhada) {
		this.listaParcelaDetalhada = listaParcelaDetalhada;
	}

	public Date getDatapagamentoParcela() {
		return datapagamentoParcela;
	}

	public void setDatapagamentoParcela(Date datapagamentoParcela) {
		this.datapagamentoParcela = datapagamentoParcela;
	}

	public boolean isCarregaLista() {
		return carregaLista;
	}

	public void setCarregaLista(boolean carregaLista) {
		this.carregaLista = carregaLista;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}

	public FormaPagamento getFormapagamento() {
		return formapagamento;
	}

	public void setFormapagamento(FormaPagamento formapagamento) {
		this.formapagamento = formapagamento;
	}

	public List<FormaPagamento> getListaFormapagamento() {
		return listaFormapagamento;
	}

	public void setListaFormapagamento(List<FormaPagamento> listaFormapagamento) {
		this.listaFormapagamento = listaFormapagamento;
	}

	public List<ParcelaPagamento> getListaParcelaAtrasada() {
		return listaParcelaAtrasada;
	}

	public void setListaParcelaAtrasada(List<ParcelaPagamento> listaParcelaAtrasada) {
		this.listaParcelaAtrasada = listaParcelaAtrasada;
	}

	
	
	
}
