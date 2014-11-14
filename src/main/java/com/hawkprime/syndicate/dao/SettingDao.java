package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.util.NodePath;

import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

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
				.createNamedQuery("Setting.findByPath")
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
					.createNamedQuery("Setting.findByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (final NoResultException ex) {
			return null;
		}
	}
}
