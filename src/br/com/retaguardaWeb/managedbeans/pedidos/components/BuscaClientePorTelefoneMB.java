package br.com.retaguardaWeb.managedbeans.pedidos.components;

import javax.inject.Named;
import java.io.Serializable;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.TelefoneCliente;
import br.com.retaguardaWeb.managedbeans.CadastroClienteMB;
import br.com.retaguardaWeb.services.ClienteService;
import br.com.retaguardaWeb.util.Acao;

@Named
@ViewScoped
public class BuscaClientePorTelefoneMB implements Serializable {
private static final long serialVersionUID = 1L;
	
	private Cliente clienteSelecionado;
	@EJB
	private ClienteService clienteService;
	private Boolean botaoInclusaoClienteVisivel;
	private Acao<Cliente> callbackCarregarCliente;
	
	@Inject
	private CadastroClienteMB cadastroClienteMB;
	
	@PostConstruct
	private void init() {
		cadastroClienteMB.setCallbackSalvarCliente(new Acao<Cliente>() {
			
			@Override
			public void executar(Cliente cliente) {
				RequestContext.getCurrentInstance().execute("modalInclusaoCliente.hide()");
				setClienteSelecionado(cliente);
				carregarCliente();
			}
		});
	}
	public List<TelefoneCliente> complete(String query) {
		List<TelefoneCliente> telefones = clienteService.buscaClientePorTelefone(query);
		
		setBotaoInclusaoClienteVisivel(Boolean.FALSE);
		
		if(telefones.size() < 1) {
			setBotaoInclusaoClienteVisivel(Boolean.TRUE);
			cadastroClienteMB.getCliente().getListaTelefones().get(0).setNumero(query);
		}
		return telefones;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public void setTelefoneSelecionado(TelefoneCliente telefoneSelecionado) {
		setClienteSelecionado(telefoneSelecionado.getCliente());
	}

	public TelefoneCliente getTelefoneSelecionado() {
		return getClienteSelecionado() == null ? null : getClienteSelecionado().getTelefone();
	}

	public Boolean getBotaoInclusaoClienteVisivel() {
		return botaoInclusaoClienteVisivel;
	}

	private void setBotaoInclusaoClienteVisivel(Boolean botaoInclusaoClienteVisivel) {
		this.botaoInclusaoClienteVisivel = botaoInclusaoClienteVisivel;
	}

	public void setCadastroClienteMB(CadastroClienteMB cadastroClienteMB) {
		this.cadastroClienteMB = cadastroClienteMB;
	}
	
	public void carregarCliente() {
		getCallbackCarregarCliente().executar(getClienteSelecionado());
	}

	private Acao<Cliente> getCallbackCarregarCliente() {
		return callbackCarregarCliente;
	}

	public void setCallbackCarregarCliente(Acao<Cliente> callbackCarregarCliente) {
		this.callbackCarregarCliente = callbackCarregarCliente;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
