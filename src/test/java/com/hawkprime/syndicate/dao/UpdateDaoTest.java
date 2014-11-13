package com.hawkprime.syndicate.dao;

import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Update DAO Tests.
 */
public class UpdateDaoTest extends AbstractDaoTest {
	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UpdateDao updateDao;

	/**
	 * Test new post percentage.
	 */
	@Test
	public void percentTest() {
		final int expectedPercentNewFeed1 = 8;
		final int expectedPercentNewFeed2 = 23;
		final long feed1Id = 1L;
		final long feed2Id = 2L;
		final long feed3Id = 3L;
		assertThat(updateDao.percentNewByFeedId(0L), is(0));
		assertThat(updateDao.percentNewByFeedId(feed1Id), is(expectedPercentNewFeed1));
		assertThat(updateDao.percentNewByFeedId(feed2Id), is(expectedPercentNewFeed2));
		assertThat(updateDao.percentNewByFeedId(feed3Id), is(0));
	}

	/**
	 * Find all entities in table.
	 */
	@Test
	public void findAllTest() {
		final int expectedUpdateCount = 6;
		List<Update> allUpdates = updateDao.findAll();
		assertThat(allUpdates.size(), is(expectedUpdateCount));
	}

	/**
	 * Find oldest update.
	 */
	@Test
	public void findOldestUpdateTest() {
		assertThat(updateDao.findOldestUpdateByFeedId(0L), is(nullValue()));

		Update update = updateDao.findOldestUpdateByFeedId(1L);
		assertThat(update.getId(), is(1L));

		final long feed2Id = 2L;
		final long expectedUpdateId = 5L;
		update = updateDao.findOldestUpdateByFeedId(feed2Id);
		assertThat(update.getId(), is(expectedUpdateId));
	}

	/**
	 * Count new posts.
	 */
	@Test
	public void coutNewPostTest() {
		final long expectedCountFeed1 = 18L;
		assertThat(updateDao.countNewPostsByFeedId(1L), is(expectedCountFeed1));

		final long feed2Id = 2L;
		final long expectedCountFeed2 = 597;
		assertThat(updateDao.countNewPostsByFeedId(feed2Id), is(expectedCountFeed2));
	}

	/**
	 * Read entities.
	 */
	@Test
	public void readUpdateTest() {
		final Long expectedTotal = 120L;
		final Long expectedNewCount = 13L;

		Update update = updateDao.findById(1L);

		assertThat(update.getTotalCount(), is(expectedTotal));
		assertThat(update.getNewCount(), is(expectedNewCount));
		assertThat(update.getFeed().getId(), is(1L));
	}

	/**
	 * Create entities.
	 */
	@Test
	@Transactional
	public void createUpdateTest() {
		final Long totalCount = 530L;
		final Long newCount = 330L;

		Update update = new UpdateBuilder()
				.withTotalCount(totalCount)
				.withNewCount(newCount)
				.withFeed(feedDao.findById(1L))
				.build();

		updateDao.create(update);

		final Long id = update.getId();
		update = null;

		update = updateDao.findById(id);

		assertThat(update.getTotalCount(), is(totalCount));
		assertThat(update.getNewCount(), is(newCount));
	}

	/**
	 * Update entities.
	 */
	@Test
	@Transactional
	public void updateUpdateTest() {
		Update update = new UpdateBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		// persist
		updateDao.create(update);

		// clear out
		final Long id = update.getId();
		update = null;

		// fetch back
		update = updateDao.findById(id);

		// change values
		final Long totalCount = 600L;
		final Long newCount = 250L;

		// update Update
		update.setTotalCount(totalCount);
		update.setNewCount(newCount);

		// persist
		updateDao.update(update);

		// clear out
		update = null;

		// fetch back
		update = updateDao.findById(id);

		// test
		assertThat(update.getTotalCount(), is(totalCount));
		assertThat(update.getNewCount(), is(newCount));
	}

	/**
	 * Delete entities.
	 */
	@Test
	@Transactional
	public void deleteUpdateTest() {
		Update update = new UpdateBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		updateDao.create(update);

		final Long id = update.getId();
		update = null;

		updateDao.deleteById(id);

		update = updateDao.findById(id);
		assertThat(update, is(nullValue()));
	}

	/**
	 * Find latest update.
	 */
	@Test
	@Transactional
	public void findLatestTest() {
		Update nonExistentUpdate = updateDao.findLatestUpdateByFeedId(0L);
		assertThat(nonExistentUpdate, is(nullValue()));

		final Long feed1Total = 120L;
		final Long feed1New = 5L;

		Update update = updateDao.findLatestUpdateByFeedId(1L);
		assertThat(update.getTotalCount(), is(feed1Total));
		assertThat(update.getNewCount(), is(feed1New));
		assertThat(update.getUpdated(), is(LocalDateTime.parse("2014-10-17T14:38:00")));

		final Long feed2Total = 980L;
		final Long feed2New = 63L;
		final long feed2Id = 2L;

		update = updateDao.findLatestUpdateByFeedId(feed2Id);
		assertThat(update.getTotalCount(), is(feed2Total));
		assertThat(update.getNewCount(), is(feed2New));
		assertThat(update.getUpdated(), is(LocalDateTime.parse("2014-10-17T18:00:01")));
	}
}
