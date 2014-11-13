package com.hawkprime.syndicate.dao;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting DAO.
 */
@Repository
public class SettingDao extends AbstractDao<Setting> {

	/**
	 * Find by path.
	 *
	 * @param path the path
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<Setting> findByPath(final NodePath path) {
		return getEntityManager()
				.createQuery("SELECT DISTINCT s "
						+ "FROM Value v "
						+ "JOIN v.node n "
						+ "JOIN v.setting s "
						+ "WHERE :path LIKE CONCAT(n.path, '%')")
				.setParameter("path", path.toString())
				.getResultList();
	}

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the setting
	 */
	public Setting findByName(final String name) {
		try {
			return (Setting) getEntityManager()
					.createQuery("SELECT s "
							+ "FROM Setting s "
							+ "WHERE name = :name")
					.setParameter("name", name)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
