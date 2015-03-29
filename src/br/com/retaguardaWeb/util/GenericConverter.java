package br.com.retaguardaWeb.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.retaguardaWeb.entidades.EntidadeComId;

@FacesConverter(forClass = EntidadeComId.class)
public class GenericConverter  implements Converter{

	@Override
    public Object getAsObject(FacesContext facesContext, UIComponent uiComponent, String value) {
        if (value != null && !value.isEmpty()) {
            return (EntidadeComId) uiComponent.getAttributes().get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object value) {
        if (value instanceof EntidadeComId) {
        	EntidadeComId entity= (EntidadeComId) value;
            if (entity != null && entity instanceof EntidadeComId && entity.getId() != null) {
                uiComponent.getAttributes().put( entity.getId().toString(), entity);
                System.out.println(entity.getId().toString());
                return entity.getId().toString();
            }
        }
        return "";
    }

}