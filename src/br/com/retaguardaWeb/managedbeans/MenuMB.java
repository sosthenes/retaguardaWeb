package br.com.retaguardaWeb.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.retaguardaWeb.entidades.LinkPerfil;
import br.com.retaguardaWeb.entidades.LinksMenu;
import br.com.retaguardaWeb.entidades.Menu;
import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.sessionbeans.LoginService;

@ManagedBean
public class MenuMB extends BasicoMB{

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
			if(model.getContents().isEmpty())
				criarMenu();
		}else{
			criarMenu();
		}
	}
	
	private List<LinkPerfil> listaLinks = new ArrayList<LinkPerfil>();

	public void criarMenu() {
		if (model == null || model.getContents().isEmpty()) {
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
				Submenu submenu = new Submenu();
				submenu.setLabel(menu.getDescricao());
				submenu.setId("menu_" + menu.getId().toString());

				for (LinksMenu subMenu : menu.getLinksMenu()) {
					MenuItem item = new MenuItem();
					item.setValue(subMenu.getDescricao());
					item.setUrl(subMenu.getPagina());
					item.setId("sid_" + subMenu.getId().toString());
					submenu.getChildren().add(item);
				}

				model.addSubmenu(submenu);
			}
			MenuItem item = new MenuItem();
			item.setValue("Sair");
			item.setId("menu_Sair");
			item.setUrl("/index.xhtml");
			
			model.addMenuItem(item);
			
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
