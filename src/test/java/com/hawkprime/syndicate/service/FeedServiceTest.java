package com.hawkprime.syndicate.service;

import com.hawkprime.syndicate.dao.FeedDao;
import com.hawkprime.syndicate.dao.UpdateDao;
import com.hawkprime.syndicate.model.Feed;
import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.FeedBuilder;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;
import com.hawkprime.syndicate.util.FrequencyCalculator;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Feed Service Tests.
 *
 * @version 5.0.0
 * @author Juan D Frias <juandfrias@gmail.com>
 */
public class FeedServiceTest {

	private final FeedService feedService = new FeedService();

	/**
	 * Post per minute test.
	 */
	@Test
	public void postPerMinuteTest() {
		Feed feed = new FeedBuilder().build();

		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final LocalDateTime now = LocalDateTime.now();
		final Long totalPosts = 20L;
		final int minutesPriorToUpdate = 5;

		when(updateDao.countNewPostsByFeedId(feed.getId()))
				.thenReturn(totalPosts);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(new UpdateBuilder()
						.withUpdated(now.minusMinutes(minutesPriorToUpdate))
						.build());

		final long expectedPostsPerMinute = 4L;
		assertThat(feedService.postPerMinute(feed), is(expectedPostsPerMinute));
	}

	/**
	 * Post per minute no updates test.
	 */
	@Test
	public void postPerMinuteNoUpdatesTest() {
		Feed feed = new FeedBuilder().build();

		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(null);

		assertThat(feedService.postPerMinute(feed), is(0L));
	}

	/**
	 * Years of post per minute test.
	 */
	@Test
	public void yearsOfPostPerMinuteTest() {
		Feed feed = new FeedBuilder().build();

		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		final LocalDateTime now = LocalDateTime.now();
		final Long totalPosts = 40000000L;
		final int yearsPriorToUpdate = 5;

		when(updateDao.countNewPostsByFeedId(feed.getId()))
				.thenReturn(totalPosts);

		when(updateDao.findOldestUpdateByFeedId(feed.getId()))
				.thenReturn(new UpdateBuilder()
						.withUpdated(now.minusYears(yearsPriorToUpdate))
						.build());

		final Long expectedPostsPerMinute = 15L;
		assertThat(feedService.postPerMinute(feed), is(expectedPostsPerMinute));
	}

	/**
	 * Save totals test.
	 */
	@Test
	public void saveTotalsTest() {
		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		Feed feed = new FeedBuilder().build();
		feedService.saveTotals(feed, 1, 0);

		verify(updateDao).create(new UpdateBuilder()
				.withFeed(feed)
				.withNewCount(0L)
				.withTotalCount(1L)
				.withUpdated(any(LocalDateTime.class))
				.build());
	}

	/**
	 * Find all test.
	 */
	@Test
	public void findAllTest() {
		FeedDao feedDao = mock(FeedDao.class);
		List<Feed> allFeeds = new ArrayList<Feed>();
		when(feedDao.findAll()).thenReturn(allFeeds);
		feedService.setFeedDao(feedDao);

		assertThat(feedService.findAll(), is(allFeeds));
	}

	/**
	 * Find active test.
	 */
	@Test
	public void findActiveTest() {
		FeedDao feedDao = mock(FeedDao.class);
		List<Feed> activeFeeds = new ArrayList<Feed>();
		when(feedDao.findActive()).thenReturn(activeFeeds);
		feedService.setFeedDao(feedDao);

		assertThat(feedService.findActiveFeeds(), is(activeFeeds));
	}

	/**
	 * Bad update frequency test.
	 */
	@Test
	public void badUpdateFrequencyTest() {
		Feed feed = new FeedBuilder()
				.withUpdateFrequency(0)
				.build();

		assertThat(feedService.needsUpdate(feed), is(false));

		feed = new FeedBuilder()
				.withUpdateFrequency(-1)
				.build();

		assertThat(feedService.needsUpdate(feed), is(false));
	}

	/**
	 * No update found test.
	 */
	@Test
	public void noUpdateFoundTest() {
		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		Feed feed = new FeedBuilder()
				.withUpdateFrequency(1)
				.build();

		when(updateDao.findLatestUpdateByFeedId(feed.getId()))
				.thenReturn(null);

		assertThat(feedService.needsUpdate(feed), is(true));
	}

	/**
	 * Update test setup.
	 *
	 * @param feedUpdateFrequency the feed update frequency
	 * @return the feed
	 */
	private Feed updateTestSetup(final int feedUpdateFrequency) {
		final int minutesSinceLastFeedUpdate = 15;

		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		Update update = new UpdateBuilder()
				.withFeed(feed)
				.withUpdated(LocalDateTime.now().minusMinutes(minutesSinceLastFeedUpdate))
				.build();

		when(updateDao.findLatestUpdateByFeedId(feed.getId()))
				.thenReturn(update);

		return feed;
	}

	/**
	 * Needs update test.
	 */
	@Test
	public void needsUpdateTest() {
		final int feedUpdateFrequency = 10;
		Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(true));
	}

	/**
	 * Needs no update test.
	 */
	@Test
	public void needsNoUpdateTest() {
		final int feedUpdateFrequency = 20;
		Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(false));
	}

	/**
	 * Right at update test.
	 */
	@Test
	public void rightAtUpdateTest() {
		final int feedUpdateFrequency = 15;
		Feed feed = updateTestSetup(feedUpdateFrequency);
		assertThat(feedService.needsUpdate(feed), is(true));
	}

	/**
	 * Update frequency test.
	 */
	@Test
	public void updateFrequencyTest() {
		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		FeedDao feedDao = mock(FeedDao.class);
		feedService.setFeedDao(feedDao);

		final int feedUpdateFrequency = 60;
		Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		final int percentNewPosts = 55;
		when(updateDao.percentNewByFeedId(feed.getId()))
				.thenReturn(percentNewPosts);

		final int newCalculatedFrequency = 80;
		FrequencyCalculator frequencyCalculator = mock(FrequencyCalculator.class);
		feedService.setFrequencyCalculator(frequencyCalculator);
		when(frequencyCalculator.calculateNewFrequency(feedUpdateFrequency, percentNewPosts))
				.thenReturn(newCalculatedFrequency);

		feedService.updateFeedFrequency(feed);

		verify(feedDao).update(feed);
	}

	/**
	 * Unchanged frequency test.
	 */
	@Test
	public void unchangedFrequencyTest() {
		UpdateDao updateDao = mock(UpdateDao.class);
		feedService.setUpdateDao(updateDao);

		FeedDao feedDao = mock(FeedDao.class);
		feedService.setFeedDao(feedDao);

		final int feedUpdateFrequency = 60;
		Feed feed = new FeedBuilder()
				.withUpdateFrequency(feedUpdateFrequency)
				.build();

		final int percentNewPosts = 75;
		when(updateDao.percentNewByFeedId(feed.getId()))
				.thenReturn(percentNewPosts);

		FrequencyCalculator frequencyCalculator = mock(FrequencyCalculator.class);
		feedService.setFrequencyCalculator(frequencyCalculator);
		when(frequencyCalculator.calculateNewFrequency(feedUpdateFrequency, percentNewPosts))
				.thenReturn(feedUpdateFrequency);

		feedService.updateFeedFrequency(feed);

		verify(feedDao, never()).update(feed);
	}
}
