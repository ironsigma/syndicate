package com.hawkprime.syndicate.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generic DAO.
 * @param <T> Entity type.
 */
public abstract class AbstractDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> entityType;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractDao() {
		entityType = (Class) ((ParameterizedType) getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public T create(final T t) {
		entityManager.persist(t);
		return t;
	}

	public void deleteById(final Object id) {
		entityManager.remove(entityManager.getReference(entityType, id));
	}

	public T findById(final Object id) {
		return entityManager.find(entityType, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		final StringBuffer queryString = new StringBuffer("SELECT e FROM ");
		queryString.append(entityType.getSimpleName()).append(" e ");
		return entityManager.createQuery(queryString.toString()).getResultList();
	}

	public T update(final T t) {
		return entityManager.merge(t);
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
