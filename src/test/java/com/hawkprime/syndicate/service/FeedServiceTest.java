package com.hawkprime.syndicate.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.dao.UpdateDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;

/**
 * Feed Service Tests.
 */
public class FeedServiceTest {

	private final FeedService feedService = new FeedService();

	@Test
	public void badUpdateFrequency() {
		Feed feed = new FeedBuilder()
				.withUpdateFrequency(0L)
				.build();

		assertFalse(feedService.needsUpdate(feed));

		feed = new FeedBuilder()
				.withUpdateFrequency(-1L)
				.build();

		assertFalse(feedService.needsUpdate(feed));
	}

	@Test
	public void noUpdateFound() {
		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final Feed feed = new FeedBuilder()
				.withUpdateFrequency(1L)
				.build();

		when(updateDao.findLatestUpdateByFeedId(feed.getId()))
				.thenReturn(null);

		assertTrue(feedService.needsUpdate(feed));
	}

	private Feed updateTestSetup(final long feedUpdateFrequency) {
		final int minutesSinceLastFeedUpdate = 15;

		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		final Update update = new UpdateBuilder()
				.withFeed(feed)
				.withUpdated(new LocalDateTime().minusMinutes(minutesSinceLastFeedUpdate))
				.build();

		when(updateDao.findLatestUpdateByFeedId(feed.getId()))
				.thenReturn(update);

		return feed;
	}

	@Test
	public void needsUpdateTest() {
		final long feedUpdateFrequency = 10L;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertTrue(feedService.needsUpdate(feed));
	}

	@Test
	public void needsNoUpdateTest() {
		final long feedUpdateFrequency = 20L;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertFalse(feedService.needsUpdate(feed));
	}

	@Test
	public void rightAtUpdateTest() {
		final long feedUpdateFrequency = 15L;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertTrue(feedService.needsUpdate(feed));
	}
}
