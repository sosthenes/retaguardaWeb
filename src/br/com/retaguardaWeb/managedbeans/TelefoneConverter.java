package br.com.retaguardaWeb.managedbeans;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.retaguardaWeb.entidades.TelefoneCliente;

@FacesConverter(value="telefoneConverter", forClass = br.com.retaguardaWeb.entidades.TelefoneCliente.class)
public class TelefoneConverter implements Converter {

	@Override
	 public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
		  	System.out.println("Estou recebendo a string " + value);
	        if (value == null || value.length() == 0) {
	            return null;
	        }
	        TelefoneCliente telefone = new TelefoneCliente();
	        telefone.setNumero(value);
	        return telefone;
	 }

	 @Override
	 public String getAsString(FacesContext fc, UIComponent uic, Object object) {
		  if (object == null) {
	            return null;
	        }
		 if(object != null && object instanceof TelefoneCliente) {  
			 if(((TelefoneCliente)object).getId()==null){
				 return null;
			 }
	            return ((TelefoneCliente)object).getId().toString();  
	        }  
	        return null;  
	    }  


}
