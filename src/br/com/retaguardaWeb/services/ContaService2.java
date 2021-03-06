package br.com.retaguardaWeb.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.retaguardaWeb.dao.ContaDAOImpl;
import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.TipoDePagamento;

@Stateless
public class ContaService2 {

	
	@Inject
	private ContaDAOImpl contaDAO;

	
	public void adiciona(Pagamento pagamento) {
		this.contaDAO.incluir(pagamento);
	}
	
	public void remover(Pagamento pagamento) {
		this.contaDAO.excluir(pagamento);
	}

	public void alterar(Pagamento pagamento) {
		this.contaDAO.atualizar(pagamento);
	}

	
	
	public List<TipoDePagamento> getTipoDePagamentos() {
		return this.contaDAO.getTipoDePagamentos();
	}

	public List<Pagamento> listar(Date dataInicio, Date dataFim) {
		return this.contaDAO.listar(dataInicio, dataFim);
	}

	public ParcelaPagamento adicionaParcela(ParcelaPagamento parcela) {
		return this.contaDAO.adicionaParcela(parcela);
	}
}
