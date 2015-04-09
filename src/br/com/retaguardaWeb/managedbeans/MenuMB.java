package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import br.com.retaguardaWeb.entidades.LinkPerfil;
import br.com.retaguardaWeb.entidades.LinksMenu;
import br.com.retaguardaWeb.entidades.Menu;
import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.services.LoginService;

@Named
public class MenuMB   extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private LoginService loginService;
	//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

	private List<Menu> listaMenu = new ArrayList<Menu>();

	private Usuario usuario = new Usuario();

	private MenuModel model;
	
	
	@PostConstruct
	private void init() {
		//HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		if(session.getAttribute("menus")!=null){
			model = new DefaultMenuModel();
			model = (MenuModel) session.getAttribute("menus");  
			if(model.getElements().isEmpty())
				criarMenu();
		}else{
			criarMenu();
		}
	}
	
	private List<LinkPerfil> listaLinks = new ArrayList<LinkPerfil>();

	public void criarMenu() {
		if (model == null || model.getElements().isEmpty()) {
			model = new DefaultMenuModel();

			for (LinkPerfil lista : getListaLinks()) {
				Menu menu = new Menu();
				if (!listaMenu.contains(lista.getIdLinksMenu().getIdMenu())) {
					menu = lista.getIdLinksMenu().getIdMenu();
					LinksMenu link = new LinksMenu();
					link = lista.getIdLinksMenu();
					menu.setLinksMenu(null);
					menu.getLinksMenu().add(link);
					listaMenu.add(menu);
				} else {
					LinksMenu link = new LinksMenu();
					link = lista.getIdLinksMenu();
					listaMenu.get(listaMenu.size() - 1).getLinksMenu()
							.add(link);
				}
			}

			for (Menu menu : getListaMenu()) {
				Submenu submenu = new DefaultSubMenu(menu.getDescricao());
				submenu.setId("menu_" + menu.getId().toString());

				for (LinksMenu subMenu : menu.getLinksMenu()) {
					MenuItem item = new DefaultMenuItem(subMenu.getDescricao(),null,subMenu.getPagina());
					item.setId("sid_" + subMenu.getId().toString());
					submenu.getElements().add(item);
				}

				model.addElement(submenu);
			}
			MenuItem item = new DefaultMenuItem("Sair",null,"/index.xhtml");
			item.setId("menu_Sair");
			
			model.addElement(item);
			
			session.setAttribute("menus", model);
		}
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}


	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public List<LinkPerfil> getListaLinks() {
		if(listaLinks==null || listaLinks.isEmpty()){
			listaLinks = loginService.listaLinks(getUsuario());
		}
		return listaLinks;
	}

	public void setListaLinks(List<LinkPerfil> listaLinks) {
		this.listaLinks = listaLinks;
	}

	@Override
	public void adiciona() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void listar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	
	
}
