package br.com.retaguardaWeb.managedbeans;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.retaguardaWeb.entidades.Funcionario;
import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.PerfilUsuario;
import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.services.FuncionarioService;
import br.com.retaguardaWeb.services.LojaService;
import br.com.retaguardaWeb.services.UsuarioService;

@Named
@ViewScoped
public class UsuarioMB extends BasicoMB implements Serializable{
private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioService usuarioService;
	@EJB
	private FuncionarioService funcionarioService;
	
	private Usuario usuarioLocal ;
	private boolean logado;
	
	private Usuario usuariosNovo;
	List<Usuario> listaUsuarios;
	@EJB
	private LojaService lojaService;
	private List<Loja> lojas;
	private Loja lojaSelecao;
	private List<Usuario> listaUsuario;
	
	
	@PostConstruct
	private void init() {
		if(usuarioLocal==null)
			usuarioLocal = new Usuario();
		if(usuariosNovo==null)
			usuariosNovo = new Usuario();
		
		if(lojas==null)
			lojas = lojaService.getLojas();
		
		if(listaUsuarios==null)
			listaUsuarios = usuarioService.listaUsuario();
			
	}

	@Override
	public void adiciona() {
		PerfilUsuario perfilAdm = new PerfilUsuario();
		perfilAdm.setId(1L);
		usuariosNovo.setIdPerfilUsuario(perfilAdm);
		usuariosNovo.setLoja(lojaSelecao);
		usuariosNovo = usuarioService.salvar(usuariosNovo);
		retornaMensagemSucessoOperacao();
		limpar();
		listaUsuarios = usuarioService.listaUsuario();
	}
	
	public void alterarSenha(){
		getUsuario().setSenha(getUsuarioLocal().getSenha());
		usuarioLocal = usuarioService.alterarSenha(getUsuario());
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.setAttribute("userManager",usuarioLocal);  
		setLogado(true);
		retornaMensagemSucessoOperacao();
	}
	
	

	
	public Usuario getUsuarioLocal() {
		return usuarioLocal;
	}




	public void setUsuarioLocal(Usuario usuarioLocal) {
		this.usuarioLocal = usuarioLocal;
	}




	public boolean isLogado() {
		return logado;
	}
	public void setLogado(boolean logado) {
		this.logado = logado;
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
		usuariosNovo = null;
		usuariosNovo.setId(null);
		usuariosNovo.setNome(null);
		usuariosNovo.setLogin(null);;
		usuariosNovo.setSenha(null);
		usuariosNovo.setEndereco(null);
		usuariosNovo.setTelefone(null);
		usuariosNovo.setEmail(null);
		
	}
	@Override
	public void editar() {
		// TODO Auto-generated method stub
		
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public Usuario getUsuariosNovo() {
		return usuariosNovo;
	}

	public void setUsuariosNovo(Usuario usuariosNovo) {
		this.usuariosNovo = usuariosNovo;
	}
	
	
	public List<Loja> getLojas() {
		return lojas;
	}

	public Loja getLojaSelecao() {
		if(lojaSelecao==null)
			lojaSelecao = new Loja();
		return lojaSelecao;
	}

	public void setLojaSelecao(Loja lojaSelecao) {
		this.lojaSelecao = lojaSelecao;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}


	
	
	
}
