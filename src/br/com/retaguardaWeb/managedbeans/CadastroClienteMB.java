package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.Endereco;
import br.com.retaguardaWeb.entidades.TelefoneCliente;
import br.com.retaguardaWeb.services.ClienteService;
import br.com.retaguardaWeb.util.Acao;

@ManagedBean(name="cadastroClienteMB")
@ViewScoped
public class CadastroClienteMB {
	private static final String CLIENTE_SALVO_COM_SUCESSO = "Cliente salvo com sucesso";

	private Cliente cliente;
	private Acao<Cliente> callbackSalvarCliente;
	
	@EJB
	private ClienteService clienteService;
	
	@PostConstruct
	public void init() {
		setCliente(new Cliente());
		getCliente().setListaTelefones(new ArrayList<TelefoneCliente>());
		getCliente().getListaTelefones().add(new TelefoneCliente(getCliente()));
		getCliente().setEnderecos(new ArrayList<Endereco>());
		setCallbackSalvarCliente(new Acao<Cliente>() {

			@Override
			public void executar(Cliente t) {
				// TODO Auto-generated method stub
			} 
		});
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public void adicionarMaisUmTelefone() {
		getCliente().getListaTelefones().add(new TelefoneCliente(getCliente()));
	}
	
	public void salvarCliente() {
		clienteService.adiciona(cliente);
		FacesContext.getCurrentInstance().addMessage("panelDadosCliente", new FacesMessage(FacesMessage.SEVERITY_INFO, CLIENTE_SALVO_COM_SUCESSO, CLIENTE_SALVO_COM_SUCESSO));
		cliente = clienteService.obterPorId(cliente);
		callbackSalvarCliente.executar(cliente);
//		cliente = new Cliente();
	}
	public Acao<Cliente> getCallbackSalvarCliente() {
		return callbackSalvarCliente;
	}
	public void setCallbackSalvarCliente(Acao<Cliente> callbackSalvarCliente) {
		this.callbackSalvarCliente = callbackSalvarCliente;
	}
	
	
	
	
}
