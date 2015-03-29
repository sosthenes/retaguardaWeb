package br.com.retaguardaWeb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.retaguardaWeb.entidades.DescricaoPagamento;
import br.com.retaguardaWeb.entidades.Usuario;

@Stateless
public class UsuarioService {

	@PersistenceContext
	private EntityManager manager;

	public Usuario alterarSenha(Usuario usuario) {
		return manager.merge(usuario);
	}

	public Usuario salvar(Usuario usuariosNovo) {
		return manager.merge(usuariosNovo);		
	}

	public List<Usuario> listaUsuario() {
		List<Usuario> lista = new ArrayList<Usuario>();
		String lcQuery = "select x from Usuario x order by x.nome";
		TypedQuery<Usuario> query = this.manager.createQuery(lcQuery, Usuario.class);
		lista = query.getResultList();
		return lista;
		
	}
	
	
	
	
}
