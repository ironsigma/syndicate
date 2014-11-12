package com.hawkprime.syndicate.service;

import java.util.HashMap;
import java.util.Map;

import org.jdom.IllegalDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.util.NodePath;

/**
 * Setting Service.
 */
@Service
public class SettingService {
	private static final Logger LOG = LoggerFactory.getLogger(SettingService.class);

	@Autowired
	private SettingDao settingDao;

	@Autowired
	private ValueDao valueDao;

	@Autowired
	private NodeDao nodeDao;

	public void setValue(final NodePath settingPath, final String value) {
		Value settingValue = valueDao.findByPath(settingPath);
		if (settingValue != null) {
			LOG.debug("Found setting \"{}\", updating value to \"{}\"", settingPath.toString(), value);
			settingValue.setValue(value);
			valueDao.update(settingValue);
			return;
		}

		final Setting setting = settingDao.findByName(settingPath.getLastComponent());
		if (setting == null) {
			throw new IllegalDataException("Setting does not exist: " + settingPath.getLastComponent());
		}

		final NodePath nodePath = settingPath.getParent();
		Node settingNode = nodeDao.findClosestByPath(nodePath);
		if (settingNode == null || !settingNode.getPath().equals(nodePath.toString())) {
			final NodePath nodePathToBuildOut;
			if (settingNode == null) {
				LOG.debug("No closest setting match found");
				nodePathToBuildOut = nodePath;
			} else {
				LOG.debug("Closest setting match was \"{}\"", settingNode.getPath());
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
					LOG.debug("Creating node \"{}/{}\"", parentNode.getPath(),
							nodePathToCreate.getLastComponent());

					path = NodePath.at(parentNode.getPath())
								.append(nodePathToCreate.getLastComponent()).toString();
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

	public void setNodeDao(final NodeDao nodeDao) {
		this.nodeDao = nodeDao;
	}

	public void setValueDao(final ValueDao valueDao) {
		this.valueDao = valueDao;
	}

	public void setSettingDao(final SettingDao settingDao) {
		this.settingDao = settingDao;
	}
}
