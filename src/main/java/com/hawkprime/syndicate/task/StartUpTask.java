package com.hawkprime.syndicate.task;

import com.hawkprime.syndicate.service.ConfigurationService;
import com.hawkprime.syndicate.util.NodePath;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Startup Task.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class StartUpTask {
	private static final Logger LOG = LoggerFactory.getLogger(StartUpTask.class);

	private static final Matcher BOOLEAN_MATCHER = Pattern.compile("(yes|true)|(no|false)", Pattern.CASE_INSENSITIVE).matcher("");
	private static final Matcher DECIMAL_MATCHER = Pattern.compile("[+-]?\\d*(\\.|\\.\\d+)?").matcher("");
	private static final Matcher INTEGER_MATCHER = Pattern.compile("[+-]?\\d+").matcher("");
	private static final Matcher DATE_MATCHER = Pattern.compile(
			"\\d{4}-[01][0-9]-[0123][0-9]"
			+ "(T[012][0-9](:[0-5][0-9](:[0-5][0-9]([.,]\\d+)?)?)?)?").matcher("");


	@Autowired
	private ConfigurationService configService;

	private boolean initialized;

	/**
	 * Startup.
	 */
	@Scheduled(initialDelay=5000, fixedDelay=Long.MAX_VALUE)
	public void startUp() {
		if (initialized) {
			LOG.debug("Application already initialized");
			return;
		}
		initialized = true;
		initializeApplication();
	}

	/**
	 * Initialize Application.
	 */
	private void initializeApplication() {
		LOG.info("Initializing Application");
		loadDefaults();
	}

	/**
	 * Load Application Defaults.
	 */
	@SuppressWarnings("unchecked")
	private void loadDefaults() {
		try {
			YamlReader yamlReader = new YamlReader(new InputStreamReader(
					this.getClass().getResourceAsStream("/configuration.yaml")));

			walkMap((Map<String, Object>) yamlReader.read(), NodePath.root());
		} catch (YamlException e) {
			LOG.error("Unable to load defaults: {}", e.getMessage());
			LOG.debug(ExceptionUtils.getStackTrace(e));
		}
	}

	/**
	 * Walk through the configuration map.
	 * @param map Map
	 * @param path current path.
	 */
	@SuppressWarnings("unchecked")
	private void walkMap(final Map<String, Object> map, final NodePath path) {
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			if (obj instanceof Map) {
				walkMap((Map<String, Object>) obj, path.append(key));

			} else if (obj instanceof List) {
				for (String item : (List<String>) obj) {
					initializeSetting(path.append(key), item);
				}

			} else {
				initializeSetting(path.append(key), (String) obj);
			}
		}
	}

	/**
	 * Initialize setting.
	 *
	 * @param path the path
	 * @param value the value
	 */
	private void initializeSetting(final NodePath path, final String value) {
		if (configService.hasValue(path)) {
			LOG.info("\"{}\" already set", path);
			return;
		}

		Object settingValue;
		if (BOOLEAN_MATCHER.reset(value).matches()) {
			if (null != BOOLEAN_MATCHER.group(1)) {
				settingValue = true;
			} else {
				settingValue = false;
			}
			LOG.info("Creatig boolean default \"{}\" => \"{}\"", path, value);

		} else if (INTEGER_MATCHER.reset(value).matches()) {
			settingValue = Integer.valueOf(value);
			LOG.info("Creatig integer default \"{}\" => \"{}\"", path, value);

		} else if (DECIMAL_MATCHER.reset(value).matches()) {
			settingValue = Double.valueOf(value);
			LOG.info("Creatig decimal default \"{}\" => \"{}\"", path, value);

		} else if (DATE_MATCHER.reset(value).matches()) {
			settingValue = new LocalDateTime(value);
			LOG.info("Creatig date default \"{}\" => \"{}\"", path, value);

		} else {
			settingValue = value;
			LOG.info("Creatig string default \"{}\" => \"{}\"", path, value);
		}

		configService.createSettingWithValue(path, settingValue);
	}

}
