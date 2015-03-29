package br.com.retaguardaWeb.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.retaguardaWeb.entidades.Usuario;
import br.com.retaguardaWeb.managedbeans.LoginMB;

@WebFilter(servletNames = {"Autenticador"})  
public class LoginFilter  implements Filter{

	
	 @Override  
	    public void doFilter(ServletRequest request, ServletResponse response,  
	            FilterChain chain) throws IOException, ServletException {  
	        // TODO Auto-generated method stub  
	        HttpServletRequest req = (HttpServletRequest) request;  
	        HttpServletResponse res = (HttpServletResponse) response;   
	        Usuario userManager = (Usuario) req.getSession().getAttribute("userManager");  
	    
	        if ((userManager == null || userManager.getId()==null || userManager.getId()==0)&& (userManager == null || userManager.getFuncionario()==null || userManager.getFuncionario().getId()==null)) {  
	            // Aqui retorno se não existir...  
	        		res.sendRedirect(req.getContextPath() + "/index.xhtml");
	        		
	        } else {  
	            // Aqui continua se existir  
	            chain.doFilter(request, response); 
	        }  
	  
	    }  
	  
	    @Override  
	    public void init(FilterConfig filterConfig) throws ServletException {  
	    }  
	  
	    @Override  
	    public void destroy() {  
	    }  
}
