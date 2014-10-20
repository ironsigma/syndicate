package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.builder.FeedBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testContext.xml")
public class FeedDaoTest {
	@Autowired
	private FeedDao feedDao;

	@Test
	public void readFeed() {
		Feed Feed = feedDao.findById(1L);
		assertEquals("MyFeed", Feed.getName());
		assertEquals("http://myfeed.com/rss", Feed.getUrl());
	}

	@Test
	@Transactional
	public void createFeed() {
		String name = "Another Feed";
		String url = "http://myfeed.com/another_Feed";
		
		Feed Feed = new FeedBuilder()
				.withName("Another Feed")
				.withUrl(url)
				.build();

		feedDao.create(Feed);

		Long id = Feed.getId();
		Feed = null;
		
		Feed = feedDao.findById(id);

		assertEquals(name, Feed.getName());
		assertEquals(url, Feed.getUrl());
	}
	
	@Test
	@Transactional
	public void updateFeed() {
		Feed Feed = new FeedBuilder().build();

		// persist
		feedDao.create(Feed);

		// clear out
		Long id = Feed.getId();
		Feed = null;

		// fetch back
		Feed = feedDao.findById(id);

		// change values
		String name = "Awsome Feed";
		String url = "http://myfeed.com/awsome_Feed";

		// update Feed
		Feed.setName(name);
		Feed.setUrl(url);

		// persist
		feedDao.update(Feed);

		// clear out
		Feed = null;

		// fetch back
		Feed = feedDao.findById(id);
		
		// test
		assertEquals(name, Feed.getName());
		assertEquals(url, Feed.getUrl());
	}

	@Test
	@Transactional
	public void deleteFeed() {
		Feed Feed = new FeedBuilder().build();

		feedDao.create(Feed);

		Long id = Feed.getId();
		Feed = null;
		
		feedDao.delete(id);

		Feed = feedDao.findById(id);
		assertNull(Feed);
	}
}
