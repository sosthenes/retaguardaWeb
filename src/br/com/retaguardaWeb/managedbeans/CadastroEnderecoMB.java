package br.com.retaguardaWeb.managedbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.Endereco;
import br.com.retaguardaWeb.services.ClienteService;
import br.com.retaguardaWeb.util.Acao;

@ManagedBean(name="cadastroEnderecoMB")
@ViewScoped
public class CadastroEnderecoMB {
	private static final String ENDERECO_SALVO_COM_SUCESSO = "Endereco salvo com sucesso";

	private Endereco endereco;
	private Acao<Endereco> callbackSalvarEndereco;
	
	@EJB
	private ClienteService clienteService;
	
	@PostConstruct
	public void init() {
		Endereco endereco = new Endereco();
		endereco.setCliente(new Cliente());
		setEndereco(endereco);
	}
	
	public void salvarEndereco() {
		Cliente cliente = clienteService.obterPorId(endereco.getCliente());
		endereco.setCliente(cliente);
		cliente.getEnderecos().add(endereco);
		clienteService.atualizar(cliente);
		FacesContext.getCurrentInstance().addMessage("panelDadosCliente", new FacesMessage(FacesMessage.SEVERITY_INFO, ENDERECO_SALVO_COM_SUCESSO, ENDERECO_SALVO_COM_SUCESSO));
		getCallbackSalvarEndereco().executar(endereco);
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Acao<Endereco> getCallbackSalvarEndereco() {
		return callbackSalvarEndereco;
	}

	public void setCallbackSalvarEndereco(Acao<Endereco> callbackSalvarEndereco) {
		this.callbackSalvarEndereco = callbackSalvarEndereco;
	}
	
	
	
	
	
	
}
