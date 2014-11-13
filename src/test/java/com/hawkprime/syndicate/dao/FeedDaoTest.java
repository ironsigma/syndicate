package com.hawkprime.syndicate.dao;

import java.util.List;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.builder.FeedBuilder;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Feed Dao Tests.
 */
public class FeedDaoTest extends AbstractDaoTest {
	@Autowired
	private FeedDao feedDao;

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		List<Feed> allFeeds = feedDao.findAll();

		// CHECKSTYLE IGNORE MagicNumber
		assertThat(allFeeds.size(), is(3));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readFeedTest() {
		Feed feed = feedDao.findById(1L);

		// CHECKSTYLE IGNORE MagicNumber
		assertThat(feed.getUpdateFrequency(), is(60));
		assertThat(feed.getName(), is("MyFeed"));
		assertThat(feed.getUrl(), is("http://myfeed.com/rss"));
		assertThat(feed.isActive(), is(true));
	}

	/**
	 * Create entities.
	 */
	@Test
	@Transactional
	public void createFeedTest() {
		final String name = "Another Feed";
		final String url = "http://myfeed.com/another_Feed";
		final Boolean active = true;
		final Integer updateFrequency = 30;

		Feed feed = new FeedBuilder()
				.withName(name)
				.withUrl(url)
				.withUpdateFrequency(updateFrequency)
				.withIsActive(active)
				.build();

		feedDao.create(feed);

		Long id = feed.getId();
		feed = null;

		feed = feedDao.findById(id);

		assertThat(feed.getName(), is(name));
		assertThat(feed.getUrl(), is(url));
		assertThat(feed.isActive(), is(active));
		assertThat(feed.getUpdateFrequency(), is(updateFrequency));
	}

	/**
	 * Update entities.
	 */
	@Test
	@Transactional
	public void updateFeedTest() {
		Feed feed = new FeedBuilder().build();

		// persist
		feedDao.create(feed);

		// clear out
		Long id = feed.getId();
		feed = null;

		// fetch back
		feed = feedDao.findById(id);

		// change values
		final String name = "Awsome Feed";
		final String url = "http://myfeed.com/awsome_Feed";
		final Boolean active = false;
		final Integer updateFrequency = 80;

		// update Feed
		feed.setName(name);
		feed.setUrl(url);
		feed.setUpdateFrequency(updateFrequency);
		feed.setActive(active);

		// persist
		feedDao.update(feed);

		// clear out
		feed = null;

		// fetch back
		feed = feedDao.findById(id);

		// test
		assertThat(feed.getName(), is(name));
		assertThat(feed.getUrl(), is(url));
		assertThat(feed.isActive(), is(active));
		assertThat(feed.getUpdateFrequency(), is(updateFrequency));
	}

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deleteFeedTest() {
		Feed feed = new FeedBuilder().build();

		feedDao.create(feed);

		final Long id = feed.getId();
		feed = null;

		feedDao.deleteById(id);

		feed = feedDao.findById(id);
		assertThat(feed, is(nullValue()));
	}

	/**
	 * find active feeds.
	 */
	@Test
	@Transactional
	public void findActiveFeedsTest() {
		List<Feed> feedList = feedDao.findActive();

		// CHECKSTYLE IGNORE MagicNumber
		assertThat(feedList.size(), is(2));
		assertThat(feedList.get(0).getId(), is(1L));
	}
}
