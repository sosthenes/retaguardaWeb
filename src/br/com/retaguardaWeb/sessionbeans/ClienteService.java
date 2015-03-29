package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.Cliente;
import br.com.retaguardaWeb.entidades.TelefoneCliente;

@Stateless
public class ClienteService {

	
	@PersistenceContext
	private EntityManager manager;

	public void adiciona(Cliente cliente) {
		this.manager.persist(cliente);
	}

	public List<TelefoneCliente> buscaClientePorTelefone(TelefoneCliente telefone) {
		List<TelefoneCliente> listaTel = new ArrayList<TelefoneCliente>();
		TypedQuery<TelefoneCliente> query = this.manager.createQuery("select x from TelefoneCliente x where  x.numero LIKE '%"+telefone.getNumero()+"%' ", TelefoneCliente.class);
		//query.setParameter("telefone",  );
		try {
			listaTel = query.getResultList();
		} catch (Exception e) {
			return null;
		}
		return listaTel;
	}

	
	public List<Cliente> getIClientes() {
		TypedQuery<Cliente> query = this.manager.createQuery("select x from Cliente x", Cliente.class);

		return query.getResultList();
	}

	public Cliente pesquisa(Cliente cliente) {
		TypedQuery<Cliente> query = this.manager.createQuery("select x from Cliente x where idCliente = :id", Cliente.class);
		query.setParameter("id", cliente.getId());
		return query.getSingleResult();
	}
}
