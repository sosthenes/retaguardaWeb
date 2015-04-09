package br.com.retaguardaWeb.sessionbeans;

import java.util.List;
import java.util.Map;

public interface CrudService<T> {
	public  T create(T t);
    public   T find(Class<T> type,Object id);
    public   T update(T t);
    public void delete(Class<T> type,Object id);
    public List<T> findWithNamedQuery(String queryName);
    public List<T> findWithNamedQuery(String queryName,int resultLimit);
    public List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters);
    public List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit);
}
