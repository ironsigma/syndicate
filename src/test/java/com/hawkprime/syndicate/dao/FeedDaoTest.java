package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.builder.FeedBuilder;

/**
 * Feed Dao Tests.
 */
public class FeedDaoTest extends AbstractDaoTest {
	@Autowired
	private FeedDao feedDao;

	@Test
	public void readFeed() {
		final Feed feed = feedDao.findById(1L);
		assertEquals("MyFeed", feed.getName());
		assertEquals("http://myfeed.com/rss", feed.getUrl());
	}

	@Test
	@Transactional
	public void createFeed() {
		final String name = "Another Feed";
		final String url = "http://myfeed.com/another_Feed";

		Feed feed = new FeedBuilder()
				.withName("Another Feed")
				.withUrl(url)
				.build();

		feedDao.create(feed);

		final Long id = feed.getId();
		feed = null;

		feed = feedDao.findById(id);

		assertEquals(name, feed.getName());
		assertEquals(url, feed.getUrl());
	}

	@Test
	@Transactional
	public void updateFeed() {
		Feed feed = new FeedBuilder().build();

		// persist
		feedDao.create(feed);

		// clear out
		final Long id = feed.getId();
		feed = null;

		// fetch back
		feed = feedDao.findById(id);

		// change values
		final String name = "Awsome Feed";
		final String url = "http://myfeed.com/awsome_Feed";

		// update Feed
		feed.setName(name);
		feed.setUrl(url);

		// persist
		feedDao.update(feed);

		// clear out
		feed = null;

		// fetch back
		feed = feedDao.findById(id);

		// test
		assertEquals(name, feed.getName());
		assertEquals(url, feed.getUrl());
	}

	@Test
	@Transactional
	public void deleteFeed() {
		Feed feed = new FeedBuilder().build();

		feedDao.create(feed);

		final Long id = feed.getId();
		feed = null;

		feedDao.delete(id);

		feed = feedDao.findById(id);
		assertNull(feed);
	}
}
