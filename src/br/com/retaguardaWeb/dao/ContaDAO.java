package br.com.retaguardaWeb.dao;

import java.util.Date;
import java.util.List;

import br.com.retaguardaWeb.entidades.Pagamento;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.TipoDePagamento;

public interface ContaDAO extends GenericDAO<Pagamento> {
	
	Pagamento recuperarPorId(Long id);
	
	List<Pagamento> pesquisar(Pagamento tipoPagamento);
	
	Pagamento recuperarPorNome(String nome);

	List<Pagamento> consultaPagamento(Pagamento pagamento);
	
	List<TipoDePagamento> getTipoDePagamentos();
	
	List<Pagamento> listar(Date dataInicio, Date dataFim);

	ParcelaPagamento adicionaParcela(ParcelaPagamento parcela) ;
}
