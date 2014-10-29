package com.hawkprime.syndicate.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.dao.UpdateDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;
import com.hawkprime.syndicate.util.FrequencyCalculator;

/**
 * Feed Service Tests.
 */
public class FeedServiceTest {

	private final FeedService feedService = new FeedService();

	@Test
	public void postPerMinuteTest() {
		final Feed feed = new FeedBuilder().build();

		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final LocalDateTime now = LocalDateTime.now();
		final Long totalPosts = 20L;

		when(updateDao.countNewPostsByFeedId(feed.getId()))
				.thenReturn(totalPosts);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(new UpdateBuilder()
						.withUpdated(now.minusMinutes(5))
						.build());

		assertThat(feedService.postPerMinute(feed), is(4L));
	}

	@Test
	public void postPerMinuteNoUpdatesTest() {
		final Feed feed = new FeedBuilder().build();

		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(null);

		assertThat(feedService.postPerMinute(feed), is(0L));
	}

	@Test
	public void yearsOfPostPerMinuteTest() {
		final Feed feed = new FeedBuilder().build();

		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final LocalDateTime now = LocalDateTime.now();
		final Long totalPosts = 40000000L;

		when(updateDao.countNewPostsByFeedId(feed.getId()))
				.thenReturn(totalPosts);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(new UpdateBuilder()
						.withUpdated(now.minusYears(5))
						.build());

		final Long expectedPostsPerMinute = 15L;
		assertThat(feedService.postPerMinute(feed), is(expectedPostsPerMinute));
	}

	@Test
	public void saveTotalsTest() {
		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final Feed feed = new FeedBuilder().build();
		feedService.saveTotals(feed, 1, 0);

		verify(updateDao).create(any(Update.class));
	}

	@Test
	public void findAllTest() {
		final FeedDao feedDao = mock(FeedDao.class);
		final List<Feed> allFeeds = new ArrayList<Feed>();
		when(feedDao.findAll()).thenReturn(allFeeds);
		feedService.setFeedDao(feedDao);

		assertThat(feedService.findAll(), is(allFeeds));
	}

	@Test
	public void findActiveTest() {
		final FeedDao feedDao = mock(FeedDao.class);
		final List<Feed> activeFeeds = new ArrayList<Feed>();
		when(feedDao.findActive()).thenReturn(activeFeeds);
		feedService.setFeedDao(feedDao);

		assertThat(feedService.findActiveFeeds(), is(activeFeeds));
	}

	@Test
	public void badUpdateFrequency() {
		Feed feed = new FeedBuilder()
				.withUpdateFrequency(0)
				.build();

		assertThat(feedService.needsUpdate(feed), is(false));

		feed = new FeedBuilder()
				.withUpdateFrequency(-1)
				.build();

		assertThat(feedService.needsUpdate(feed), is(false));
	}

	@Test
	public void noUpdateFound() {
		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final Feed feed = new FeedBuilder()
				.withUpdateFrequency(1)
				.build();

		when(updateDao.findLatestUpdateByFeedId(feed.getId()))
				.thenReturn(null);

		assertThat(feedService.needsUpdate(feed), is(true));
	}

	private Feed updateTestSetup(final int feedUpdateFrequency) {
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
		final int feedUpdateFrequency = 10;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(true));
	}

	@Test
	public void needsNoUpdateTest() {
		final int feedUpdateFrequency = 20;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(false));
	}

	@Test
	public void rightAtUpdateTest() {
		final int feedUpdateFrequency = 15;
		final Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(true));
	}

	@Test
	public void updateFrequencyTest() {
		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final FeedDao feedDao = mock(FeedDao.class);
		feedService.setFeedDao(feedDao);

		final int feedUpdateFrequency = 60;
		final Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		final int percentNewPosts = 55;
		when(updateDao.percentNewByFeedId(feed.getId()))
				.thenReturn(percentNewPosts);

		final int newCalculatedFrequency = 80;
		final FrequencyCalculator frequencyCalculator = mock(FrequencyCalculator.class);
		feedService.setFrequencyCalculator(frequencyCalculator);
		when(frequencyCalculator.calculateNewFrequency(feedUpdateFrequency, percentNewPosts))
				.thenReturn(newCalculatedFrequency);

		feedService.updateFeedFrequency(feed);

		verify(feedDao).update(feed);
	}

	@Test
	public void unchangedFrequencyTest() {
		final UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final FeedDao feedDao = mock(FeedDao.class);
		feedService.setFeedDao(feedDao);

		final int feedUpdateFrequency = 60;
		final Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		final int percentNewPosts = 75;
		when(updateDao.percentNewByFeedId(feed.getId()))
				.thenReturn(percentNewPosts);

		final FrequencyCalculator frequencyCalculator = mock(FrequencyCalculator.class);
		feedService.setFrequencyCalculator(frequencyCalculator);
		when(frequencyCalculator.calculateNewFrequency(feedUpdateFrequency, percentNewPosts))
				.thenReturn(feedUpdateFrequency);

		feedService.updateFeedFrequency(feed);

		verify(feedDao, never()).update(feed);
	}
}
