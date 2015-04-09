package br.com.retaguardaWeb.services;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.LinkPerfil;
import br.com.retaguardaWeb.entidades.LinksMenu;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Menu;
import br.com.retaguardaWeb.entidades.ParcelaPagamento;
import br.com.retaguardaWeb.entidades.PerfilUsuario;
import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.services.crudutils.CrudService;
import br.com.retaguardaWeb.util.QueryParameter;

@Stateless
public class LoginService {

	@PersistenceContext
	private EntityManager manager;

	@Inject
	private CrudService<Funcionario> crudService;

	public Usuario validaUsuario(Usuario usuario) {
		try {
			Query query = manager
					.createQuery(
							"select x from Usuario x where x.login = :login and x.senha = :senha",
							Usuario.class);
			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", usuario.getSenha());
			return (Usuario) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Menu> listaMenuPerfil(PerfilUsuario perfilEdicao) {
		Query query = manager.createQuery("select x from Menu x ", Menu.class);
		List<Menu> lista = new ArrayList<Menu>();
		List<Menu> listaComAcessos = new ArrayList<Menu>();
		lista = (List<Menu>) query.getResultList();

		Usuario usuario = new Usuario();
		usuario.setIdPerfilUsuario(perfilEdicao);

		int iFora = 0;
		List<LinkPerfil> listaPefilHabilitado = listaLinks(usuario);
		for (Menu menu : lista) {
			Menu menuComAcesso = new Menu();
			menuComAcesso = menu;
			listaComAcessos.add(menuComAcesso);

			int i = 0;
			for (LinksMenu link : listaComAcessos.get(iFora).getLinksMenu()) {
				for (LinkPerfil linkPerfil : listaPefilHabilitado) {
					if (listaComAcessos.get(iFora).getLinksMenu().get(i) == linkPerfil
							.getIdLinksMenu()) {
						listaComAcessos.get(iFora).getLinksMenu().get(i)
								.setAcesso(true);
					}
				}
				// menuComAcesso.setLinksMenu(listaLinkAcesso);
				i++;
			}
			iFora++;
		}

		return listaComAcessos;
	}

	public List<LinkPerfil> listaLinks(Usuario usuario) {
		Query query = manager.createQuery(
				"select x from LinkPerfil x where x.idPerfilusuario = :perfil"
						+ " order by x.idLinksMenu.idMenu", LinkPerfil.class);
		query.setParameter("perfil", usuario.getIdPerfilUsuario());
		List<LinkPerfil> lista = (List<LinkPerfil>) query.getResultList();
		return lista;
	}

	public List<PerfilUsuario> listaPerfilPorLoja(Loja loja) {
		Query query = manager.createQuery("select x from PerfilUsuario x "
				+ " order by x.descricao", PerfilUsuario.class);
		List<PerfilUsuario> lista = (List<PerfilUsuario>) query.getResultList();
		return lista;
	}

	public Funcionario validaFuncionario(Usuario usuario) {
		return crudService.findOneResultWithNamedQuery(
				Funcionario.OBTER_USUARIO_POR_LOGIN_E_SENHA,
				QueryParameter.with("login", usuario.getLogin())
						.and("senha", usuario.getSenha()).parameters());
	}

	public void excluirPerfil(PerfilUsuario perfilEdicao) {
		String sql = "delete FROM linkperfil where idPerfilusuario = ?";
		try {
			Query query_ = manager.createNativeQuery(sql,
					ParcelaPagamento.class);
			query_.setParameter(1, perfilEdicao.getId());
			query_.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void alteraPerfil(LinkPerfil linkPerfil) {
		manager.persist(linkPerfil);
	}

}
