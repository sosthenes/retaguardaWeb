package br.com.retaguardaWeb.sessionbeans;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import br.com.retaguardaWeb.entidades.Produto;

@Stateful
public class CarrinhoBean  {

	private LinkedHashSet<Produto> produtos = new LinkedHashSet<Produto>();
	

	private static int contadorTotal;
	private static int contadorAtivos;
	private int id;

	public void adicionaMeia(Produto produto) throws Exception {
		produto.setMeia(true);
		this.produtos.add(produto);
	}
	
	public void adiciona(Produto produto) throws Exception {
		this.produtos.add(produto);
	}

	public void remove(Produto produto) {
		this.produtos.remove(produto);
		
	}
	
	
	public Set<Produto> getProdutos() {
		return produtos;
	}

	@Remove
	public void finalizaCompra() {
		System.out.println(" Finalizando a compra ");
	}

	@PostConstruct
	public void postContruct() {
		synchronized (CarrinhoBean.class) {
			CarrinhoBean.contadorTotal++;
			CarrinhoBean.contadorAtivos++;
			this.id = CarrinhoBean.contadorTotal;

			System.out.println(" PostConstruct ");
			System.out.println("ID: " + this.id);
			System.out
					.println(" ContatorTotal : " + CarrinhoBean.contadorTotal);
			System.out.println(" ContatorAtivos : "
					+ CarrinhoBean.contadorAtivos);
		}
	}

	@PrePassivate
	public void prePassivate() {
		synchronized (CarrinhoBean.class) {
			CarrinhoBean.contadorAtivos--;

			System.out.println(" PrePassivate ");
			System.out.println("ID: " + this.id);
			System.out
					.println(" ContatorTotal : " + CarrinhoBean.contadorTotal);
			System.out.println(" ContatorAtivos : "
					+ CarrinhoBean.contadorAtivos);
		}
	}

	@PostActivate
	public void postActivate() {
		synchronized (CarrinhoBean.class) {
			CarrinhoBean.contadorAtivos++;

			System.out.println(" PostActivate ");
			System.out.println("ID: " + this.id);
			System.out
					.println(" ContatorTotal : " + CarrinhoBean.contadorTotal);
			System.out.println(" ContatorAtivos : "
					+ CarrinhoBean.contadorAtivos);
		}
	}

	@PreDestroy
	public void preDestroy() {
		synchronized (CarrinhoBean.class) {
			CarrinhoBean.contadorAtivos--;

			System.out.println(" PreDestroy ");
			System.out.println("ID: " + this.id);
			System.out
					.println(" ContatorTotal : " + CarrinhoBean.contadorTotal);
			System.out.println(" ContatorAtivos : "
					+ CarrinhoBean.contadorAtivos);
		}
	}


	@PreDestroy
	public void destruindo() {
		System.out.println(" Mais um carrinho será destruído ... ");
	}

	@PrePassivate
	public void passivando() {
		System.out.println(" Mais um carrinho será passivado ... ");
	}

	@PostActivate
	public void ativando() {
		System.out.println(" Mais um carrinho foi ativado ... ");
	}






}