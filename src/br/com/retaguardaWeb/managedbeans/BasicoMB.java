package br.com.retaguardaWeb.managedbeans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.retaguardaWeb.entidades.Loja;
import br.com.retaguardaWeb.entidades.Usuario;


public abstract class  BasicoMB implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Usuario usuario = new Usuario();
	public abstract void adiciona ();
	public abstract void listar() ;
	public abstract void remover();
	public abstract void limpar();
	public abstract void editar();
	
	protected ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
	
	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
	DecimalFormat qtdeParser = new DecimalFormat( "0.00");
	
	private Loja loja ;
	
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	
	public void retornaMensagemWARN(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,mensagem));
	}
	
	public void retornaMensagemSucesso(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,mensagem,mensagem));
	}
	
	public void retornaMensagemSucessoOperacao(){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Opera��o realizada com sucesso","Opera��o realizada com sucesso"));
	}
	
	public void retornaMensagemErro(String mensagem){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,mensagem,mensagem));
	}
	
	
	public Usuario getUsuario() {
		if(usuario==null || usuario.getId()==null){
			usuario = (Usuario) session.getAttribute("userManager");  
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public HttpSession getSession() {
		return session;
	}
	public void setSession(HttpSession session) {
		this.session = session;
	}
	public Loja getLoja() {
		if(loja==null || loja.getId()==null){
			loja = getUsuario().getLoja();
			if(loja==null){
				loja = getUsuario().getFuncionario().getLoja();
			}
		}
		return loja;
	}
	public void setLoja(Loja loja) {
		this.loja = loja;
	}
	

	
	
}
