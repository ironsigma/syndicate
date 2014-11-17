package com.hawkprime.syndicate.service;

import com.hawkprime.syndicate.dao.NodeDao;
import com.hawkprime.syndicate.dao.SettingDao;
import com.hawkprime.syndicate.dao.ValueDao;
import com.hawkprime.syndicate.model.Node;
import com.hawkprime.syndicate.model.Setting;
import com.hawkprime.syndicate.model.Value;
import com.hawkprime.syndicate.model.builder.NodeBuilder;
import com.hawkprime.syndicate.model.builder.SettingBuilder;
import com.hawkprime.syndicate.model.builder.ValueBuilder;
import com.hawkprime.syndicate.util.NodePath;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.jdom.IllegalDataException;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


/**
 * Setting tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class SettingServiceTest {
	private static final String VERSION_VALUE = "1.0.0";
	private static final NodePath APP_VERSION_PATH = NodePath.at("/App/version");
	private static final NodePath UPDATE_INTERVAL_PATH = NodePath.at("/App/Feed/1/UpdateInterval");

	private SettingService settingService;
	private SettingDao settingDao;
	private ValueDao valueDao;
	private NodeDao nodeDao;

	/**
	 * Test setup.
	 */
	@Before
	public void setUp() {
		settingDao = mock(SettingDao.class);
		valueDao = mock(ValueDao.class);
		nodeDao = mock(NodeDao.class);
		settingService = new SettingService();
		settingService.setSettingDao(settingDao);
		settingService.setValueDao(valueDao);
		settingService.setNodeDao(nodeDao);
	}

	/**
	 * Setup mock values for DAOs.
	 * @param closestParent closest parent.
	 * @param path Path for value
	 * @return Setting object.
	 */
	private Setting setupMockValues(final NodePath closestParent, final NodePath path) {
		Setting setting = new SettingBuilder()
				.withName(path.getLastComponent())
				.build();

		when(settingDao.findByName(path.getLastComponent()))
				.thenReturn(setting);

		when(nodeDao.findClosestByPath(path.getParent()))
				.thenReturn(
					new NodeBuilder()
						.withPath(closestParent.toString())
						.build()
				);

		return setting;
	}

	/**
	 * Verify nodes and value where created.
	 *
	 * @param setting Setting to verify.
	 * @param parent parent node
	 * @param child child node
	 */
	private void verifyNodeCreate(final Setting setting, final NodePath parent, final NodePath child) {
		Node parentNode = new NodeBuilder()
					.withParent(null)
					.withPath(parent.toString())
					.build();

		NodePath pathDiff = parent.getPathDifferences(child.getParent());

		if (pathDiff != null) {
			for (NodePath pathComponent : pathDiff) {
				if (pathComponent.equals(NodePath.root())) {
					continue;
				}
				Node childNode = new NodeBuilder()
						.withParent(parentNode)
						.withPath(parent.append(pathComponent).toString())
						.build();
				verify(nodeDao).create(childNode);
				parentNode = childNode;
			}
		}

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(parentNode)
				.build());
	}

	/**
	 * Generic set value test.
	 * @param parentPath parent path
	 * @param childPath child path
	 */
	private void setValueTest(final NodePath parentPath, final NodePath childPath) {
		Setting setting = setupMockValues(parentPath, childPath);
		settingService.setValue(childPath, VERSION_VALUE);
		verifyNodeCreate(setting, parentPath, childPath);
	}

	/**
	 * New setting, deep existing sub levels, multiple non existing path.
	 */
	@Test
	public void newSettingDeepExistingSubLevelsMultipleNonExistingPathTest() {
		setValueTest(NodePath.at("/App/User/1/Feed"),
				NodePath.at("/App/User/1/Feed/Meta/Internal/Build/version"));
	}

	/**
	 * New setting, multiple non existing path.
	 */
	@Test
	public void newSettingMultipleNonExistingPathTest() {
		setValueTest(NodePath.at("/App/Meta"),
				NodePath.at("/App/Meta/Internal/Build/version"));
	}

	/**
	 * New setting, one non existing path.
	 */
	@Test
	public void newSettingOneNonExistingPathTest() {
		NodePath versionPath = NodePath.at("/App/Meta/version");
		setValueTest(versionPath.getParent().getParent(), versionPath);
	}

	/**
	 * New setting, existing path.
	 */
	@Test
	public void newSettingExistingPathTest() {
		setValueTest(APP_VERSION_PATH.getParent(), APP_VERSION_PATH);
	}

	/**
	 * Non existing root.
	 */
	@Test
	public void nonExistingRootTest() {
		NodePath versionPath = NodePath.at("/version");
		Setting setting = new SettingBuilder()
				.withName(versionPath.getLastComponent())
				.build();

		when(settingDao.findByName(versionPath.getLastComponent()))
				.thenReturn(setting);

		settingService.setValue(versionPath, VERSION_VALUE);

		Node rootNode = new NodeBuilder()
				.withParent(null)
				.withPath("/")
				.build();

		verify(nodeDao).create(rootNode);

		verify(valueDao).create(new ValueBuilder()
				.withValue(VERSION_VALUE)
				.withSetting(setting)
				.withNode(rootNode)
				.build());
	}

	/**
	 * Existing setting, existing path.
	 */
	@Test
	public void existingSettingExistingPathTest() {
		Value value = new ValueBuilder()
				.build();

		when(valueDao.findByPath(APP_VERSION_PATH))
				.thenReturn(value);

		settingService.setValue(APP_VERSION_PATH, VERSION_VALUE);

		verify(valueDao).update(value);
	}

	/**
	 * Non existing setting.
	 */
	@Test(expected=IllegalDataException.class)
	public void nonExistingSettingExistingPathTest() {
		when(valueDao.findByPath(APP_VERSION_PATH))
				.thenReturn(null);

		settingService.setValue(APP_VERSION_PATH, VERSION_VALUE);
	}

	/**
	 * Gets the settings test.
	 */
	@Test
	public void getSettingsTest() {
		settingService.setValueDao(valueDao);
		settingService.setSettingDao(settingDao);

		Setting updateIntervalSetting = new SettingBuilder().withName("UpdateInterval").build();
		Setting maxUpdatesSetting = new SettingBuilder().withName("MaxUpdates").build();
		List<Setting> settingList = Arrays.asList(new Setting[] {
				updateIntervalSetting,
				maxUpdatesSetting,
		});

		NodePath path = NodePath.at("/App/Feed/1");
		when(settingDao.findByPath(path))
				.thenReturn(settingList);

		Value updateIntervalValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(updateIntervalSetting)
				.build();

		when(valueDao.findByPath(path.append(updateIntervalSetting.getName())))
				.thenReturn(updateIntervalValue);

		Value maxUpdatesValue = new ValueBuilder()
				.withValue(1)
				.withNode(new NodeBuilder().build())
				.withSetting(maxUpdatesSetting)
				.build();

		when(valueDao.findByPath(path.append(maxUpdatesSetting.getName())))
				.thenReturn(maxUpdatesValue);

		final int expectedNumberOfSettings = 2;
		Map<String, Object> settings = settingService.getSettings(path);
		assertThat(settings, is(not(nullValue())));
		assertThat(settings.size(), is(expectedNumberOfSettings));
		assertThat(settings.containsKey(updateIntervalSetting.getName()), is(true));
		assertThat(settings.get(updateIntervalSetting.getName()), is(updateIntervalValue.getValue()));
		assertThat(settings.containsKey(maxUpdatesSetting.getName()), is(true));
		assertThat(settings.get(maxUpdatesSetting.getName()), is(updateIntervalValue.getValue()));
	}

	/**
	 * Gets the value test.
	 */
	@Test
	public void getValueTest() {
		final Integer expectedValue = 60;
		settingService.setValueDao(valueDao);

		Value value = new ValueBuilder()
				.withValue(expectedValue)
				.build();

		when(valueDao.findByPath(UPDATE_INTERVAL_PATH))
				.thenReturn(value);

		Integer testValue = settingService.getValue(UPDATE_INTERVAL_PATH, Integer.class);
		assertThat(testValue, is(not(nullValue())));
		assertThat(testValue, is(expectedValue));

		testValue = settingService.getValue(NodePath.at("/App/Feed/1/xxxUpdateInterval"), Integer.class);
		assertThat(testValue, is(nullValue()));
	}

	/**
	 * Bad cast test.
	 */
	@Test(expected=ClassCastException.class)
	public void badCastTest() {
		settingService.setValueDao(valueDao);

		Value value = new ValueBuilder()
				.withValue(0)
				.build();

		when(valueDao.findByPath(UPDATE_INTERVAL_PATH))
				.thenReturn(value);

		String testValue = settingService.getValue(UPDATE_INTERVAL_PATH, String.class);
		assertThat(testValue, is(nullValue()));
	}
}
