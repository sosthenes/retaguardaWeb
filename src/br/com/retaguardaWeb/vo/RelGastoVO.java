package br.com.retaguardaWeb.vo;

import java.util.List;

public class RelGastoVO {

	private List<GastoVO> totalGastoPorTipoConta;
	
	private List<GastoVO> listaGastoPorTipoConta;
	
	private List<GastoVO> listaGastoPorDescri��o;
	
	private GastoVO gastoVO;
	
	
	

	public GastoVO getGastoVO() {
		return gastoVO;
	}

	public void setGastoVO(GastoVO gastoVO) {
		this.gastoVO = gastoVO;
	}

	/**
	 * @return the totalGastoPorTipoConta
	 */
	public List<GastoVO> getTotalGastoPorTipoConta() {
		return totalGastoPorTipoConta;
	}

	/**
	 * @param totalGastoPorTipoConta the totalGastoPorTipoConta to set
	 */
	public void setTotalGastoPorTipoConta(List<GastoVO> totalGastoPorTipoConta) {
		this.totalGastoPorTipoConta = totalGastoPorTipoConta;
	}

	/**
	 * @return the listaGastoPorTipoConta
	 */
	public List<GastoVO> getListaGastoPorTipoConta() {
		return listaGastoPorTipoConta;
	}

	/**
	 * @param listaGastoPorTipoConta the listaGastoPorTipoConta to set
	 */
	public void setListaGastoPorTipoConta(List<GastoVO> listaGastoPorTipoConta) {
		this.listaGastoPorTipoConta = listaGastoPorTipoConta;
	}

	/**
	 * @return the listaGastoPorDescri��o
	 */
	public List<GastoVO> getListaGastoPorDescri��o() {
		return listaGastoPorDescri��o;
	}

	/**
	 * @param listaGastoPorDescri��o the listaGastoPorDescri��o to set
	 */
	public void setListaGastoPorDescri��o(List<GastoVO> listaGastoPorDescri��o) {
		this.listaGastoPorDescri��o = listaGastoPorDescri��o;
	}
	
	
	
	
	
	
}
