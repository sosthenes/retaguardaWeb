package br.com.retaguardaWeb.vo;

import java.util.List;

public class GastoVO {

	private String tipoConta;
	private String descricao;
	private String valor;
	private String formaPgto;
	private String parcela;
	private String pago;
	private String acao;
	private String periodicidade;
	private String diaPagamento;
	private String dataCompra;
	private String dataPagameno;
	private String formaPagamento;
	private String porcentagem;
	private String total;
	
	private List<GastoVO> listaGasto;	
	
	
	
	public List<GastoVO> getListaGasto() {
		return listaGasto;
	}
	public void setListaGasto(List<GastoVO> listaGasto) {
		this.listaGasto = listaGasto;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getPorcentagem() {
		return porcentagem;
	}
	public void setPorcentagem(String porcentagem) {
		this.porcentagem = porcentagem;
	}
	/**
	 * @return the tipoConta
	 */
	public String getTipoConta() {
		return tipoConta;
	}
	/**
	 * @param tipoConta the tipoConta to set
	 */
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return the formaPgto
	 */
	public String getFormaPgto() {
		return formaPgto;
	}
	/**
	 * @param formaPgto the formaPgto to set
	 */
	public void setFormaPgto(String formaPgto) {
		this.formaPgto = formaPgto;
	}
	/**
	 * @return the parcela
	 */
	public String getParcela() {
		return parcela;
	}
	/**
	 * @param parcela the parcela to set
	 */
	public void setParcela(String parcela) {
		this.parcela = parcela;
	}
	/**
	 * @return the pago
	 */
	public String getPago() {
		return pago;
	}
	/**
	 * @param pago the pago to set
	 */
	public void setPago(String pago) {
		this.pago = pago;
	}
	/**
	 * @return the acao
	 */
	public String getAcao() {
		return acao;
	}
	/**
	 * @param acao the acao to set
	 */
	public void setAcao(String acao) {
		this.acao = acao;
	}
	/**
	 * @return the periodicidade
	 */
	public String getPeriodicidade() {
		return periodicidade;
	}
	/**
	 * @param periodicidade the periodicidade to set
	 */
	public void setPeriodicidade(String periodicidade) {
		this.periodicidade = periodicidade;
	}
	/**
	 * @return the diaPagamento
	 */
	public String getDiaPagamento() {
		return diaPagamento;
	}
	/**
	 * @param diaPagamento the diaPagamento to set
	 */
	public void setDiaPagamento(String diaPagamento) {
		this.diaPagamento = diaPagamento;
	}
	/**
	 * @return the dataCompra
	 */
	public String getDataCompra() {
		return dataCompra;
	}
	/**
	 * @param dataCompra the dataCompra to set
	 */
	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}
	/**
	 * @return the dataPagameno
	 */
	public String getDataPagameno() {
		return dataPagameno;
	}
	/**
	 * @param dataPagameno the dataPagameno to set
	 */
	public void setDataPagameno(String dataPagameno) {
		this.dataPagameno = dataPagameno;
	}
	/**
	 * @return the formaPagamento
	 */
	public String getFormaPagamento() {
		return formaPagamento;
	}
	/**
	 * @param formaPagamento the formaPagamento to set
	 */
	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	
	
	
}
