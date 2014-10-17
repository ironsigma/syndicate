package com.hawkprime.syndicate.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericDao<T> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	private Class<T> entityType;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDao() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		entityType = (Class) pt.getActualTypeArguments()[0];
	}

	public T create(T t) {
		entityManager.persist(t);
		return t;
	}

	public void delete(Object id) {
		entityManager.remove(entityManager.getReference(entityType, id));
	}

	public T findById(Object id) {
		return (T) entityManager.find(entityType, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		final StringBuffer queryString = new StringBuffer("SELECT e FROM ");
		queryString.append(entityType.getSimpleName()).append(" e ");
		return (List<T>) entityManager.createQuery(queryString.toString()).getResultList();
	}

	public T update(T t) {
		return entityManager.merge(t);
	}

}
