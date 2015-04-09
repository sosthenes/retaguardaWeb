package br.com.retaguardaWeb.services.crudutils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Local(CrudService.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CrudServiceBean<T> implements CrudService<T> {

	@PersistenceContext
	EntityManager em;

	public T create(T t) {
		this.em.persist(t);
		this.em.flush();
		this.em.refresh(t);
		return t;
	}

	public T find(Class<T> type, Object id) {
		return (T) this.em.find(type, id);
	}

	public void delete(Class<T> type, Object id) {
		Object ref = this.em.getReference(type, id);
		this.em.remove(ref);
	}

	public T update(T t) {
		return (T) this.em.merge(t);
	}

	@SuppressWarnings("unchecked")
	public List<T> findWithNamedQuery(String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	public List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters) {
		return findWithNamedQuery(namedQueryName, parameters, 0);
	}

	@SuppressWarnings("unchecked")
	public List<T> findWithNamedQuery(String queryName, int resultLimit) {
		return this.em.createNamedQuery(queryName).setMaxResults(resultLimit)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByNativeQuery(String sql, Class<T> type) {
		return this.em.createNativeQuery(sql, type).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findWithNamedQuery(String namedQueryName, Map<String,Object> parameters,int resultLimit){
        Set<Entry<String, Object>> rawParameters = parameters.entrySet();
        Query query = this.em.createNamedQuery(namedQueryName);
        if(resultLimit > 0)
            query.setMaxResults(resultLimit);
        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

	@Override
	public T findOneResultWithNamedQuery(String namedQueryName,
			Map<String, Object> parameters) {
		List<T> result = findWithNamedQuery(namedQueryName, parameters);
		return result.size() > 0 ? result.get(0) : null;
	}
}