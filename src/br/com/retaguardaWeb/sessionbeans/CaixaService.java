package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Caixa;
import br.com.retaguardaWeb.entidades.CaixaPeriodoFuncionario;
import br.com.retaguardaWeb.entidades.FormaPagamento;
import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.PeriodoTrabalho;
import br.com.retaguardaWeb.entidades.ValoresFechamentoCaixa;
import br.com.retaguardaWeb.util.Conversoes;

@Stateless
public class CaixaService {

	

	@PersistenceContext
	private EntityManager manager;
	
	@EJB
	private PedidoService pedidoService;
	
	@EJB
	private FormaPagamentoService formaPgtoService;
	

	public void adiciona(Caixa caixa) {
		//this.manager.merge(caixa);
	}
	
	public void remover(Caixa caixa) {
	//	this.manager.remove(caixa);
	}

	public void alterar(Caixa caixa) {
		//this.manager.merge(caixa);
	}

	
	
	public List<Caixa> getCaixas(Loja loja) {
		TypedQuery<Caixa> query = this.manager.createQuery(
				"select x from Caixa x where x.idLoja=:loja", Caixa.class);
		query.setParameter("loja", loja);
		return query.getResultList();
	}

	public Caixa caixaPorId(Caixa ingred) {
		return this.manager.find(Caixa.class, ingred);
	}

	public List<Caixa> listaCaixaDisponivel() {
 		
		
		return null;
	}
	
	public List<CaixaPeriodoFuncionario> recuperaCaixaAberto(Loja loja, PeriodoTrabalho periodo){
		List<CaixaPeriodoFuncionario> caixaPeriodo = new ArrayList<CaixaPeriodoFuncionario>();
		try {
			TypedQuery<CaixaPeriodoFuncionario> query = this.manager.createQuery(
					"select x from CaixaPeriodoFuncionario x"
							+ " where x.idLoja=:loja"
							+ " and x.periodoTrabalho=:periodo", CaixaPeriodoFuncionario.class);
			query.setParameter("loja", loja);
			query.setParameter("periodo", periodo);
			caixaPeriodo = query.getResultList();
			
		} catch (Exception e) {
			System.out.println("Aqui");
		}
		
		return caixaPeriodo;
		
	}

	public CaixaPeriodoFuncionario atualizaCaixa(CaixaPeriodoFuncionario caixaPeriodoFuncionario) {
		try {
			caixaPeriodoFuncionario = manager.merge(caixaPeriodoFuncionario);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return caixaPeriodoFuncionario;
	}

	public boolean recuperaCaixaAbertoFuncionario(Loja loja, PeriodoTrabalho periodo, Funcionario funcionario) {
		try {
			String sqlQuery = "select x from CaixaPeriodoFuncionario x"
					+ " where x.idLoja=:loja"
					+ " and x.periodoTrabalho=:periodo";
			if(funcionario!=null && funcionario.getId()!=null)
				sqlQuery+= " and x.funcionario = :funcionario"
				
					+ " and x.dataHoraAbertura IS NOT NULL"
					+ " and x.dataHoraFechamento IS NULL";
					
					
			CaixaPeriodoFuncionario c = new CaixaPeriodoFuncionario();
			TypedQuery<CaixaPeriodoFuncionario> query = this.manager.createQuery(
					sqlQuery, CaixaPeriodoFuncionario.class);
			query.setParameter("loja", loja);
			query.setParameter("periodo", periodo);
			if(funcionario!=null && funcionario.getId()!=null)
				query.setParameter("funcionario", funcionario);
			c= query.getSingleResult();
			
			if(c!=null && c.getDataHoraAbertura()!=null && c.getDataHoraFechamento()==null){
				return true;
			}else {
				return false;
			}
			
			
		} catch (Exception e) {
			return false;
		}
	}

	public CaixaPeriodoFuncionario recuperaCaixaAberto(Loja loja, PeriodoTrabalho periodo, Funcionario funcionario) {
		try {
			TypedQuery<CaixaPeriodoFuncionario> query = this.manager.createQuery(
					"select x from CaixaPeriodoFuncionario x"
							+ " where x.idLoja=:loja"
							+ " and x.periodoTrabalho=:periodo"
							+ " and x.funcionario = :funcionario"
							+ " and x.dataHoraAbertura IS NOT NULL"
							+ " and x.dataHoraFechamento IS NULL"
							+ " order by x.id desc", CaixaPeriodoFuncionario.class);
			query.setParameter("loja", loja);
			query.setParameter("periodo", periodo);
			query.setParameter("funcionario", funcionario);
			return query.getSingleResult();
			
		} catch (Exception e) {
			return null;
		}
	}

	public boolean verificaCaixaAberto(Loja loja, PeriodoTrabalho periodo,
			Funcionario funcionario) {
		if(recuperaCaixaAbertoFuncionario(loja, periodo, funcionario)){
			return true;
		}else{
			return false;
		}
	}

	public CaixaPeriodoFuncionario pegaValoresCaixa(PeriodoTrabalho periodoTrabalhoAtual, CaixaPeriodoFuncionario valoresCaixaAtual) {
		List<FormaPagamento> listaFormaPagamento;
		listaFormaPagamento = formaPgtoService.listaFormaPagamento();
		List<ValoresFechamentoCaixa> listaValorer = new ArrayList<ValoresFechamentoCaixa>();
		Conversoes conv = new Conversoes();
		for(FormaPagamento f : listaFormaPagamento){
			ValoresFechamentoCaixa valor = new ValoresFechamentoCaixa();
			valor.setCaixaPeriodoTrabalho(valoresCaixaAtual);
			valor.setIdFormaPagamento(f);
			Double valorTotal = 0.00;
			valorTotal=		totalPedidos(valoresCaixaAtual, f);
			if(valorTotal>0){
				valor.setValor(conv.converteDoubleToString(valorTotal));
			}else{
				valor.setValor("0,00");
			}
			listaValorer.add(valor);
		}
		valoresCaixaAtual.setListaValoresFechamentoCaixa(listaValorer);
		valoresCaixaAtual.setValorTotalVendido(totalPedidos(valoresCaixaAtual, null));
		return valoresCaixaAtual;
	}

	public double totalPedidos(CaixaPeriodoFuncionario valoresCaixaAtual,
			FormaPagamento f) {
		Double valor = null;
		valor = pedidoService.getTotalPedidos(valoresCaixaAtual, f);
		if(valor==null){
			valor = 0.00;
		}
		return valor;
	}
	
}
