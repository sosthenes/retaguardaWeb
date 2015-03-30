package br.com.retaguardaWeb.managedbeans.pedidos;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.managedbeans.pedidos.components.BuscaClientePorTelefoneMB;
import br.com.retaguardaWeb.managedbeans.pedidos.components.SelecionarEnderecoMB;
import br.com.retaguardaWeb.util.Acao;

@ManagedBean(name="cadastroPedidoMB")
@ViewScoped
public class CadastroPedidoMB {
	
	@ManagedProperty("#{buscaClientePorTelefoneMB}")
	private BuscaClientePorTelefoneMB buscaClientePorTelefoneMB;
	@ManagedProperty("#{selecionarEnderecoMB}")
	private SelecionarEnderecoMB selecionarEnderecoMB;
	
	@PostConstruct
	public void init() {
			
		buscaClientePorTelefoneMB.setCallbackCarregarCliente(new Acao<Cliente>() {
			private SelecionarEnderecoMB selecionarEnderecoClosureMB = selecionarEnderecoMB;
			@Override
			public void executar(Cliente cliente) {
				selecionarEnderecoClosureMB.setCliente(cliente);
				RequestContext.getCurrentInstance().update("principal:panelSelecionarEndereco");
			}
		});
	}

	public void setSelecionarEnderecoMB(SelecionarEnderecoMB selecionarEnderecoMB) {
		this.selecionarEnderecoMB = selecionarEnderecoMB;
	}

	public void setBuscaClientePorTelefoneMB(
			BuscaClientePorTelefoneMB buscaClientePorTelefoneMB) {
		this.buscaClientePorTelefoneMB = buscaClientePorTelefoneMB;
	}
	
	
	
	
	
	
	
}
