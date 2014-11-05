package com.hawkprime.syndicate.dao;

import org.springframework.stereotype.Repository;

import com.hawkprime.syndicate.model.Setting;

/**
 * Configuration DAO.
 */
@Repository
public class SettingDao extends AbstractDao<Setting> {

	public Setting findByPath(final String path) {
		return null;
	}
}
