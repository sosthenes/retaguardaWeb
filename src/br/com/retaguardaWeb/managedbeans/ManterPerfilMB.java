package br.com.retaguardaWeb.managedbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.retaguardaWeb.entidades.LinkPerfil;
import br.com.retaguardaWeb.entidades.LinksMenu;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Menu;
import br.com.retaguardaWeb.entidades.PerfilUsuario;
import br.com.retaguardaWeb.sessionbeans.FuncionarioService;
import br.com.retaguardaWeb.sessionbeans.LoginService;

@ManagedBean
@ViewScoped
public class ManterPerfilMB extends BasicoMB{

	private List<PerfilUsuario> perfilUsuario;
	
	@EJB
	private LoginService loginService;
	@EJB
	private FuncionarioService funcionarioService;
	
	private List<Menu> listaMenuPerfil;
	
	private Loja loja = new Loja();
	
	private Long idPerfilUsuario;
	
	private Long idFuncionario;
	
	private PerfilUsuario perfilEdicao;
	
	private String[] idLinkMenu;
	private String[] idLinkPerfil;
	private boolean[] habilitado;
	
	
	@Override
	public void adiciona() {

	}
	
	@PostConstruct
	private void init() {
		if(perfilUsuario==null){
			//getLoja().setId(1L);
			perfilUsuario = loginService.listaPerfilPorLoja(getLoja());
		}
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

	public void listaLinksPerfis(){
		if(perfilEdicao!=null){
			listaMenuPerfil = loginService.listaMenuPerfil(perfilEdicao);
		}
		
		
		
	}
	
	@Override
	public void editar() {
		loginService.excluirPerfil(perfilEdicao);
		for(Menu teste : listaMenuPerfil){
			System.out.println(teste.getDescricao());
			for(LinksMenu lisk : teste.getLinksMenu()){
				if(lisk.isAcesso()){
					LinkPerfil liskAltera = new LinkPerfil();
					liskAltera.setIdLinksMenu(lisk);
					liskAltera.setIdPerfilusuario(perfilEdicao);
					loginService.alteraPerfil(liskAltera);
					System.out.println(lisk.getDescricao() + " - " + lisk.isAcesso());
				}
				
			}
		}
		retornaMensagemSucessoOperacao();
	}


	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public List<PerfilUsuario> getPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(List<PerfilUsuario> perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}


	public Long getIdPerfilUsuario() {
		return idPerfilUsuario;
	}

	public void setIdPerfilUsuario(Long idPerfilUsuario) {
		this.idPerfilUsuario = idPerfilUsuario;
	}

	public List<Menu> getListaMenuPerfil() {
		return listaMenuPerfil;
	}

	public void setListaMenuPerfil(List<Menu> listaMenuPerfil) {
		this.listaMenuPerfil = listaMenuPerfil;
	}

	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}


	public PerfilUsuario getPerfilEdicao() {
		if(perfilEdicao==null)
			perfilEdicao = new PerfilUsuario();
		return perfilEdicao;
	}

	public void setPerfilEdicao(PerfilUsuario perfilEdicao) {
		this.perfilEdicao = perfilEdicao;
	}


	public String[] getIdLinkMenu() {
		return idLinkMenu;
	}

	public void setIdLinkMenu(String[] idLinkMenu) {
		this.idLinkMenu = idLinkMenu;
	}

	public String[] getIdLinkPerfil() {
		return idLinkPerfil;
	}

	public void setIdLinkPerfil(String[] idLinkPerfil) {
		this.idLinkPerfil = idLinkPerfil;
	}

	public boolean[] getHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean[] habilitado) {
		this.habilitado = habilitado;
	}


	 
	
	
}
