package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hawkprime.syndicate.model.Value;

/**
 * Value Dao Tests.
 */
public class ValueDaoTest extends AbstractDaoTest {
	@Autowired
	private ValueDao valueDao;

	@Test
	public void findByPath() {
		Value value = valueDao.findByPath("/App/Feed/1", "UpdateInterval");
		assertThat(value, is(not(nullValue())));
		assertThat(value.getType(), is("integer"));
		assertThat(value.getValue(), is("60"));

		value = valueDao.findByPath("/xxx", "UpdateInterval");
		assertThat(value, is(nullValue()));
	}

//	/**
//	 * Find all entities in table.
//	 */
//	@Test
//	public void findAllTest() {
//		final List<Value> allValues = valueDao.findAll();
//		assertThat(allValues.size(), is(3));
//	}
//
//	/**
//	 * Read entities.
//	 */
//	@Test
//	public void readValueTest() {
//		final Integer expectedUpdateFrequency = 60;
//		final Value value = valueDao.findById(1L);
//		assertThat(value.getName(), is("MyValue"));
//		assertThat(value.getUrl(), is("http://myvalue.com/rss"));
//		assertThat(value.isActive(), is(true));
//		assertThat(value.getUpdateFrequency(), is(expectedUpdateFrequency));
//	}
//
//	/**
//	 * Create entities.
//	 */
//	@Test
//	@Transactional
//	public void createValueTest() {
//		final String name = "Another Value";
//		final String url = "http://myvalue.com/another_Value";
//		final Boolean active = true;
//		final Integer updateFrequency = 30;
//
//		Value value = new ValueBuilder()
//				.withName(name)
//				.withUrl(url)
//				.withUpdateFrequency(updateFrequency)
//				.withIsActive(active)
//				.build();
//
//		valueDao.create(value);
//
//		final Long id = value.getId();
//		value = null;
//
//		value = valueDao.findById(id);
//
//		assertThat(value.getName(), is(name));
//		assertThat(value.getUrl(), is(url));
//		assertThat(value.isActive(), is(active));
//		assertThat(value.getUpdateFrequency(), is(updateFrequency));
//	}
//
//	/**
//	 * Update entities.
//	 */
//	@Test
//	@Transactional
//	public void updateValueTest() {
//		Value value = new ValueBuilder().build();
//
//		// persist
//		valueDao.create(value);
//
//		// clear out
//		final Long id = value.getId();
//		value = null;
//
//		// fetch back
//		value = valueDao.findById(id);
//
//		// change values
//		final String name = "Awsome Value";
//		final String url = "http://myvalue.com/awsome_Value";
//		final Boolean active = false;
//		final Integer updateFrequency = 80;
//
//		// update Value
//		value.setName(name);
//		value.setUrl(url);
//		value.setUpdateFrequency(updateFrequency);
//		value.setActive(active);
//
//		// persist
//		valueDao.update(value);
//
//		// clear out
//		value = null;
//
//		// fetch back
//		value = valueDao.findById(id);
//
//		// test
//		assertThat(value.getName(), is(name));
//		assertThat(value.getUrl(), is(url));
//		assertThat(value.isActive(), is(active));
//		assertThat(value.getUpdateFrequency(), is(updateFrequency));
//	}
//
//	/**
//	 * Delete entities.
//	 */
//	@Test
//	@Transactional
//	public void deleteValueTest() {
//		Value value = new ValueBuilder().build();
//
//		valueDao.create(value);
//
//		final Long id = value.getId();
//		value = null;
//
//		valueDao.deleteById(id);
//
//		value = valueDao.findById(id);
//		assertThat(value, is(nullValue()));
//	}
}
