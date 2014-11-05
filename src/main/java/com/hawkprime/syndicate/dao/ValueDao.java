package com.hawkprime.syndicate.dao;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Value;

/**
 * Configuration DAO.
 */
@Repository
public class ValueDao extends AbstractDao<Value> {

	/**
	 * Find by path.
	 *
	 * @param path the path
	 * @param settingName the setting name
	 * @return the value
	 */
	public Value findByPath(final String path, final String settingName) {
		try {
			return (Value) getEntityManager()
					.createQuery("SELECT v "
							+ "FROM Value v "
							+ "JOIN v.node n "
							+ "JOIN v.setting s "
							+ "WHERE :path LIKE CONCAT(n.path, '%') "
							+ "AND s.name = :name "
							+ "ORDER BY LENGTH(n.path) DESC")
					.setParameter("path", path)
					.setParameter("name", settingName)
					.setMaxResults(1)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
