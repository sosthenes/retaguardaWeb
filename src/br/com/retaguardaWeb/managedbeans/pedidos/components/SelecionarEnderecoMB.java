package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.Endereco;
import br.com.retaguardaWeb.managedbeans.CadastroEnderecoMB;
import br.com.retaguardaWeb.services.ClienteService;
import br.com.retaguardaWeb.util.Acao;

@ViewScoped
@Named
public class SelecionarEnderecoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Cliente cliente;
	private Endereco enderecoSelecionado;
	
	@Inject
	private CadastroEnderecoMB cadastroEnderecoMB;
	
	@EJB
	private ClienteService clienteService;
	
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void populaEndereco(Endereco end){
		enderecoSelecionado = end;
	}
	public Endereco getEnderecoSelecionado() {
		return enderecoSelecionado;
	}
	public void setEnderecoSelecionado(Endereco enderecoSelecionado) {
		this.enderecoSelecionado = enderecoSelecionado;
	}
	
	public void abrirModalCadastroEndereco() {
		cadastroEnderecoMB.getEndereco().setCliente(getCliente());
		cadastroEnderecoMB.setCallbackSalvarEndereco(new Acao<Endereco>() {
			
			@Override
			public void executar(Endereco endereco) {
				setCliente(endereco.getCliente());
				RequestContext.getCurrentInstance().execute("modalInclusaoEndereco.hide()");
				RequestContext.getCurrentInstance().update("principal:panelSelecionarEndereco");
			}
		});
		RequestContext.getCurrentInstance().execute("modalInclusaoEndereco.show()");
		RequestContext.getCurrentInstance().update("principal:modalInclusaoEndereco");
	}
	
	
	
	
	
	public void setCadastroEnderecoMB(CadastroEnderecoMB cadastroEnderecoMB) {
		this.cadastroEnderecoMB = cadastroEnderecoMB;
	}
	
	
	
}
