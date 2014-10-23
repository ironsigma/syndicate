package com.hawkprime.syndicate.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

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
	public void findAllTest() {
		final int expectedUpdateCount = 5;
		final List<Update> allUpdates = updateDao.findAll();
		assertThat(allUpdates.size(), is(expectedUpdateCount));
	}
	@Test
	public void findOldestUpdateTest() {
		assertThat(updateDao.findOldestUpdateByFeedId(0L), is(nullValue()));

		Update update = updateDao.findOldestUpdateByFeedId(1L);
		assertThat(update.getId(), is(1L));

		update = updateDao.findOldestUpdateByFeedId(2L);
		assertThat(update.getId(), is(5L));
	}

	@Test
	public void coutNewPostTest() {
		final long expectedCountFeed1 = 18L;
		assertThat(updateDao.countNewPosts(1L), is(expectedCountFeed1));

		final long expectedCountFeed2 = 597;
		assertThat(updateDao.countNewPosts(2L), is(expectedCountFeed2));
	}

	@Test
	public void readUpdate() {
		final Long expectedTotal = 120L;
		final Long expectedNewCount = 13L;

		final Update update = updateDao.findById(1L);
		assertThat(update.getTotalCount(), is(expectedTotal));
		assertThat(update.getNewCount(), is(expectedNewCount));
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

		assertThat(update.getTotalCount(), is(totalCount));
		assertThat(update.getNewCount(), is(newCount));
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
		assertThat(update.getTotalCount(), is(totalCount));
		assertThat(update.getNewCount(), is(newCount));
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
		assertThat(update, is(nullValue()));
	}

	@Test
	@Transactional
	public void findLatest() {
		final Update nonExistentUpdate = updateDao.findLatestUpdateByFeedId(0L);
		assertThat(nonExistentUpdate, is(nullValue()));

		final Long feed1Total = 120L;
		final Long feed1New = 5L;

		Update update = updateDao.findLatestUpdateByFeedId(1L);
		assertThat(update.getTotalCount(), is(feed1Total));
		assertThat(update.getNewCount(), is(feed1New));
		assertThat(update.getUpdated(), is(LocalDateTime.parse("2014-10-17T14:38:00")));

		final Long feed2Total = 980L;
		final Long feed2New = 63L;

		update = updateDao.findLatestUpdateByFeedId(2L);
		assertThat(update.getTotalCount(), is(feed2Total));
		assertThat(update.getNewCount(), is(feed2New));
		assertThat(update.getUpdated(), is(LocalDateTime.parse("2014-10-17T18:00:01")));
	}
}
