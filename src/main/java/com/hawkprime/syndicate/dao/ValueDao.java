package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

/**
 * Value DAO.
 */
@Repository
public class ValueDao extends AbstractDao<Value> {

	/**
	 * Find by path.
	 *
	 * @param path the path
	 * @return the value
	 */
	public Value findByPath(final NodePath path) {
		try {
			return (Value) getEntityManager()
					.createNamedQuery("Value.findByPath")
					.setParameter("path", path.getParent().toString())
					.setParameter("name", path.getLastComponent())
					.setMaxResults(1)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
