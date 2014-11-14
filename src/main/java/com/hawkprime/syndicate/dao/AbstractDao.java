package com.hawkprime.syndicate.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generic DAO.
 * @param <T> Entity type.
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public abstract class AbstractDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> entityType;

	/**
	 * Get entity type.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractDao() {
		entityType = (Class) ((ParameterizedType) getClass()
				.getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	/**
	 * Create entity.
	 * @param entity Entity
	 * @return Persisted entity
	 */
	public T create(final T entity) {
		entityManager.persist(entity);
		return entity;
	}

	/**
	 * Delete entity.
	 * @param id Entity id
	 */
	public void deleteById(final Object id) {
		entityManager.remove(entityManager.getReference(entityType, id));
	}

	/**
	 * Find by id.
	 * @param id Entity id
	 * @return Entity found, null otherwise
	 */
	public T findById(final Object id) {
		return entityManager.find(entityType, id);
	}

	/**
	 * Find all entities.
	 * @return List of entities found, empty list if none found
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		final StringBuffer queryString = new StringBuffer("SELECT e FROM ");
		queryString.append(entityType.getSimpleName()).append(" e ");
		return entityManager.createQuery(queryString.toString()).getResultList();
	}

	/**
	 * Update entity.
	 * @param entity Entity to update
	 * @return Entity updated
	 */
	public T update(final T entity) {
		return entityManager.merge(entity);
	}

	/**
	 * Get entity manager.
	 * @return entity manager
	 */
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
