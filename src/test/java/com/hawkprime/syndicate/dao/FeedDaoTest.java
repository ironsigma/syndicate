package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

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
	public void findAllTest() {
		final List<Feed> allFeeds = feedDao.findAll();
		assertThat(allFeeds.size(), is(3));
	}

	@Test
	public void readFeedTest() {
		final Integer expectedUpdateFrequency = 60;
		final Feed feed = feedDao.findById(1L);
		assertThat(feed.getName(), is("MyFeed"));
		assertThat(feed.getUrl(), is("http://myfeed.com/rss"));
		assertThat(feed.isActive(), is(true));
		assertThat(feed.getUpdateFrequency(), is(expectedUpdateFrequency));
	}

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

		final Long id = feed.getId();
		feed = null;

		feed = feedDao.findById(id);

		assertThat(feed.getName(), is(name));
		assertThat(feed.getUrl(), is(url));
		assertThat(feed.isActive(), is(active));
		assertThat(feed.getUpdateFrequency(), is(updateFrequency));
	}

	@Test
	@Transactional
	public void updateFeedTest() {
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

	@Test
	@Transactional
	public void findActiveFeedsTest() {
		final Long activeFeedId = 1L;
		final List<Feed> feedList = feedDao.findActive();
		assertThat(feedList.size(), is(2));
		assertThat(feedList.get(0).getId(), is(activeFeedId));
	}
}
