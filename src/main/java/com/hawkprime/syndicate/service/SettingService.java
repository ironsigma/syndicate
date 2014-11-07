package com.hawkprime.syndicate.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting Service.
 */
@Service
public class SettingService {
	@Autowired
	private SettingDao settingDao;

	@Autowired
	private ValueDao valueDao;

	@Autowired
	private NodeDao nodeDao;

	public Map<String, Object> getSettings(final NodePath path) {
		final Map<String, Object> map = new HashMap<String, Object>();
		for (Setting setting : settingDao.findByPath(path)) {
			map.put(setting.getName(), valueDao.findByPath(path.append(setting.getName())).getValue());
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(final NodePath path, final Class<T> type) {
		final Value value = valueDao.findByPath(path);
		if (value == null) {
			return null;
		}
		return (T) value.getValue();
	}

	public void setValueDao(final ValueDao valueDao) {
		this.valueDao = valueDao;
	}

	public void setSettingDao(final SettingDao settingDao) {
		this.settingDao = settingDao;
	}
}
