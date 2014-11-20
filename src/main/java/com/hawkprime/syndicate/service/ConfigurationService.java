package com.hawkprime.syndicate.service;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;

import java.util.HashMap;
import java.util.Map;

import org.jdom.IllegalDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Setting Service.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
@Service
public class ConfigurationService {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationService.class);

	@Autowired
	private SettingDao settingDao;

	@Autowired
	private ValueDao valueDao;

	@Autowired
	private NodeDao nodeDao;

	/**
	 * Sets the value.
	 *
	 * @param <T> Generic type
	 * @param settingPath the setting path
	 * @param value the value
	 */
	public <T> void setValue(final NodePath settingPath, final T value) {
		setValue(settingPath, value, false);
	}

	/**
	 * Sets the value.
	 *
	 * @param <T> the generic type
	 * @param settingPath the setting path
	 * @param value the value
	 * @param createSetting true if setting is to be created, false to throw exception if not found.
	 */
	private <T> void setValue(final NodePath settingPath, final T value, final boolean createSetting) {
		Value settingValue = valueDao.findByPath(settingPath);
		if (settingValue != null) {
			LOG.debug("Found setting \"{}\", updating value to \"{}\"", settingPath.toString(), value);
			settingValue.setValue(value);
			valueDao.update(settingValue);
			return;
		}

		final Setting setting = getSetting(settingPath.getLastComponent(), createSetting);
		final NodePath nodePath = settingPath.getParent();
		Node settingNode = nodeDao.findClosestByPath(nodePath);
		if (settingNode == null || !settingNode.getPath().equals(nodePath.toString())) {
			final NodePath nodePathToBuildOut;
			if (settingNode == null) {
				LOG.debug("No path to setting found");
				nodePathToBuildOut = nodePath;
			} else {
				LOG.debug("Closest path to setting was \"{}\"", settingNode.getPath());
				nodePathToBuildOut = nodePath.getPathDifferences(NodePath.at(settingNode.getPath()));
			}
			LOG.debug("Will have to build out \"{}\"", nodePathToBuildOut.toString());
			Node parentNode = settingNode;
			Node newNode;
			for (NodePath nodePathToCreate : nodePathToBuildOut) {
				String path;
				if (nodePathToCreate.equals(NodePath.root())) {
					if (settingNode != null) {
						continue;
					}
					LOG.debug("Creating root node \"/\"");
					path = NodePath.root().toString();

				} else {
					path = NodePath.at(parentNode.getPath()).append(nodePathToCreate.getLastComponent()).toString();
					LOG.debug("Creating node \"{}\"", path);
				}

				newNode = new Node();
				newNode.setParent(parentNode);
				newNode.setPath(path);
				nodeDao.create(newNode);
				parentNode = newNode;
			}
			settingNode = parentNode;
			LOG.debug("Setting node is now \"{}\"", settingNode.getPath());
		}

		settingValue = new Value();
		settingValue.setValue(value);
		settingValue.setSetting(setting);
		settingValue.setNode(settingNode);
		LOG.debug("Creating new setting value: {}", settingValue.toString());
		valueDao.create(settingValue);
	}

	/**
	 * Find the setting by name.
	 * @param name Name of setting
	 * @param create Create setting if it doesn't exist
	 * @return the setting
	 */
	private Setting getSetting(final String name, final boolean create) {
		Setting setting = settingDao.findByName(name);
		if (setting == null) {
			if (create) {
				LOG.debug("Creating setting \"{}\"", name);
				setting = new Setting();
				setting.setName(name);
				setting = settingDao.create(setting);
			} else {
				throw new IllegalDataException("Setting does not exist: " + name);
			}
		}
		return setting;
	}

	/**
	 * Gets the settings.
	 *
	 * @param path the path
	 * @return the settings
	 */
	public Map<String, Object> getSettings(final NodePath path) {
		final Map<String, Object> map = new HashMap<String, Object>();
		for (Setting setting : settingDao.findByPath(path)) {
			map.put(setting.getName(), valueDao.findByPath(path.append(setting.getName())).getValue());
		}
		return map;
	}

	/**
	 * Gets the value.
	 *
	 * @param <T> the generic type
	 * @param path path
	 * @param defaultValue value if not found
	 * @return the value
	 */
	public <T extends Object> T getValue(final NodePath path, final T defaultValue) {
		@SuppressWarnings("unchecked")
		T value = getValue(path, (Class<T>) defaultValue.getClass());
		if (value == null) {
			LOG.debug("No value found for \"{}\" using default \"{}\"", path.toString(), defaultValue);
			setValue(path, defaultValue, true);
			return defaultValue;
		}
		return value;
	}

	/**
	 * Gets the value.
	 *
	 * @param <T> the generic type
	 * @param path the path
	 * @param type the type
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(final NodePath path, final Class<T> type) {
		final Value value = valueDao.findByPath(path);
		if (value == null) {
			return null;
		}
		return (T) value.getValue();
	}

	/**
	 * Sets the node DAO.
	 *
	 * @param nodeDao the new node DAO
	 */
	public void setNodeDao(final NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	/**
	 * Sets the value DAO.
	 *
	 * @param valueDao the new value DAO
	 */
	public void setValueDao(final ValueDao valueDao) {
		this.valueDao = valueDao;
	}

	/**
	 * Sets the setting DAO.
	 *
	 * @param settingDao the new setting DAO
	 */
	public void setSettingDao(final SettingDao settingDao) {
		this.settingDao = settingDao;
	}
}
