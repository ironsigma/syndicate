package com.hawkprime.syndicate.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkprime.syndicate.dao.ConfigDao;
import com.hawkprime.syndicate.model.Config;

/**
 * The Class ConfigService.
 */
@Service
public class ConfigService {
	@Autowired
	private ConfigDao configDao;

	public void setValue(final Configurable configurable, final String key) {
		final Object value = configDao.getConfigValue(configurable.getSection(), key, null, configurable.getReferenceId());
	}

	/**
	 * Gets the configuration.
	 *
	 * @param configurable the configurable
	 * @return the configuration
	 */
	public Map<String, Object> getConfiguration(final Configurable configurable) {
		final Map<String, Object> map = new HashMap<String, Object>();
		final List<Config> configList = configDao.getConfiguration(configurable.getSection(), configurable.getReferenceId());
		for (final Config config : configList) {
			map.put(config.getKey(), config.getValue());
		}
		return map;
	}
}
