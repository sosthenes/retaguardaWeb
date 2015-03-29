package br.com.retaguardaWeb.dao;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.retaguardaWeb.entidades.EntidadeBase;

public class GenericDAOImpl<T extends EntidadeBase> implements GenericDAO<T> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericDAOImpl.class);
	
	@PersistenceContext
	protected EntityManager manager;
	
	@Override
	public T incluir(T entity) {
		manager.persist(entity);
		return entity;
	}

	@Override
	public T atualizar(T entity) {
		return manager.merge(entity);
	}

	@Override
	public void excluir(T entity) {
		manager.remove(sincronizar(entity));
	}

	@Override
	public T sincronizar(T entity) {
		T newEntity = null;
		
		if (!manager.contains(entity)) {
			newEntity = manager.merge(entity);
		}
		manager.refresh(newEntity == null ? entity : newEntity);
		return newEntity == null ? entity : newEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> recuperarTodos(Class<T> entityClass) {
		Query query = manager.createQuery(buscarTodosJPQL(entityClass.getSimpleName()));
		try {
			return (List<T>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> recuperarPorParametro(T entity) {
		StringBuilder sb = new StringBuilder();
		sb.append(buscarTodosJPQL(entity.getClass().getSimpleName()));
		Class cls = entity.getClass();
		Properties props = new Properties();
		boolean isAnd = false;
		do {
			try {
				Field fieldlist[] = cls.getDeclaredFields();
				for (int i = 0; i < fieldlist.length; i++) {
					Field field = fieldlist[i];
					field.setAccessible(true);
					Collection coll = null;

					if ((!field.getName().equals("serialVersionUID"))
							&& (field.get(entity) != null)) {
						if (field.get(entity) instanceof Collection) {
							coll = (Collection) field.get(entity);
						}
						if ((coll == null) || (!coll.isEmpty())) {
							sb.append(isAnd ? " and " : " where ");
							isAnd = true;
							sb.append(" o." + field.getName() + "= :"
									+ field.getName());
							props.put(field.getName(), field.get(entity));
						}
					}
				}
				cls = cls.getSuperclass();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			}
		} while (!cls.getName().equals("java.lang.Object"));
		Query query = manager.createQuery(sb.toString());
		for (Enumeration en = props.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, props.get(key));
		}
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<Object> executarQueryJPQL(String jpql, HashMap<String, Object> parametros)
			throws IllegalStateException {
		Query query = manager.createQuery(jpql);

		Set<String> chaves = parametros.keySet();  
		
		for (Iterator<String> iterator = chaves.iterator(); iterator.hasNext();) {
			String chave = iterator.next();  
			query.setParameter(chave,  parametros.get(chave));
		}

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executarQueryJPQL(String jpql)
			throws IllegalStateException {
		Query query = manager.createQuery(jpql);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public int excluirJPQL(String jpql, Properties parametros) {
		return executaUpdate(jpql, parametros);
	}

	@Override
	public int atualizarJPQL(String jpql, Properties parametros) {
		return executaUpdate(jpql, parametros);
	}

	@Override
	public Object executaJPQL(String jpql) throws IllegalStateException {
		Query query = manager.createQuery(jpql);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object executaJPQL(String jpql, Properties parametros)
			throws IllegalStateException {
		Query query = manager.createQuery(jpql);
		for (Enumeration en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executaNativeQuery(String sql, Properties parametros)
			throws IllegalStateException {
		Query query = manager.createNativeQuery(sql);
		for (Enumeration<?> en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> executaNativeQuery(String sql)
			throws IllegalStateException {
		Query query = manager.createNativeQuery(sql);
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object executaNativeQuerySingleResult(String sql,
			Properties parametros) throws IllegalStateException {
		Query query = manager.createNativeQuery(sql);
		for (Enumeration en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<T> executaJpqlListResult(StringBuilder jpql,
			Properties parametros) throws IllegalStateException {
		Query query = manager.createQuery(jpql.toString());

		for (Enumeration en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}

		try {
			return (List<T>) query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public T executaJpqlUniqueResult(StringBuilder jpql, Properties parametros)
			throws IllegalStateException {
		Query query = manager.createQuery(jpql.toString());
		for (Enumeration en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}

		try {
			return (T) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void flush() {
		manager.flush();
	}
	
	private String buscarTodosJPQL(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT o FROM ");
		sb.append(name);
		sb.append(" o");
		return sb.toString();
	}
	
	@SuppressWarnings("rawtypes")
	private int executaUpdate(String jpql, Properties parametros) {
		Query query = manager.createQuery(jpql);
		for (Enumeration en = parametros.propertyNames(); en.hasMoreElements();) {
			String key = (String) en.nextElement();
			query.setParameter(key, parametros.get(key));
		}
		return query.executeUpdate();
	}

}
