package com.hawkprime.syndicate.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hawkprime.syndicate.model.Update;
import com.hawkprime.syndicate.model.builder.UpdateBuilder;

/**
 * Update DAO Tests.
 */
public class UpdateDaoTest extends AbstractDaoTest {
	@Autowired
	private FeedDao feedDao;

	@Autowired
	private UpdateDao updateDao;

	@Test
	public void readUpdate() {
		final Long expectedTotal = 120L;
		final Long expectedNewCount = 13L;

		final Update update = updateDao.findById(1L);
		assertEquals(expectedTotal, update.getTotalCount());
		assertEquals(expectedNewCount, update.getNewCount());
	}

	@Test
	@Transactional
	public void createUpdate() {
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

		assertEquals(totalCount, update.getTotalCount());
		assertEquals(newCount, update.getNewCount());
	}

	@Test
	@Transactional
	public void updateUpdate() {
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
		assertEquals(totalCount, update.getTotalCount());
		assertEquals(newCount, update.getNewCount());
	}

	@Test
	@Transactional
	public void deleteUpdate() {
		Update update = new UpdateBuilder()
				.withFeed(feedDao.findById(1L))
				.build();

		updateDao.create(update);

		final Long id = update.getId();
		update = null;

		updateDao.delete(id);

		update = updateDao.findById(id);
		assertNull(update);
	}

	@Test
	@Transactional
	public void findLatest() {
		final Update nonExistentUpdate = updateDao.findLatestUpdateByFeedId(0L);
		assertNull(nonExistentUpdate);

		final Long feed1Total = 120L;
		final Long feed1New = 5L;

		Update update = updateDao.findLatestUpdateByFeedId(1L);
		assertEquals(feed1Total, update.getTotalCount());
		assertEquals(feed1New, update.getNewCount());
		assertEquals(LocalDateTime.parse("2014-10-17T14:38:00"), update.getUpdated());

		final Long feed2Total = 980L;
		final Long feed2New = 63L;

		update = updateDao.findLatestUpdateByFeedId(2L);
		assertEquals(feed2Total, update.getTotalCount());
		assertEquals(feed2New, update.getNewCount());
		assertEquals(LocalDateTime.parse("2014-10-17T18:00:01"), update.getUpdated());
	}
}
