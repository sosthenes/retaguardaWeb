package br.com.retaguardaWeb.managedbeans.pedidos.components;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.TelefoneCliente;
import br.com.retaguardaWeb.sessionbeans.ClienteService;

@ManagedBean(name="buscaClientePorTelefoneMB")
@ViewScoped
public class BuscaClientePorTelefoneMB {
	
	private Cliente clienteSelecionado;
	@EJB
	private ClienteService clienteService;
	public void verificarInclusaoCliente() {
		if(clienteSelecionado == null) {
			RequestContext.getCurrentInstance().execute("modalInclusaoCliente.show()");
		}
	}
	
	public List<TelefoneCliente> complete(String query) {
		return clienteService.buscaClientePorTelefone(query);
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
	
	
	
	
	
	
	
	
}
