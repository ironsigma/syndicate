package com.hawkprime.syndicate.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting DAO.
 */
@Repository
public class SettingDao extends AbstractDao<Setting> {

	@SuppressWarnings("unchecked")
	public List<Setting> findByPath(final NodePath path) {
		return getEntityManager()
				.createQuery("SELECT DISTINCT s "
						+ "FROM Value v "
						+ "JOIN v.node n "
						+ "JOIN v.setting s "
						+ "WHERE :path LIKE CONCAT(n.path, '%') ")
				.setParameter("path", path.toString())
				.getResultList();
	}
}
